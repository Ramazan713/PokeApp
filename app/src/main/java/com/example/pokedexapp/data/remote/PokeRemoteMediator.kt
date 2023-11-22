package com.example.pokedexapp.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.pokedexapp.data.local.AppDatabase
import com.example.pokedexapp.data.local.entities.MovesEntity
import com.example.pokedexapp.data.local.entities.PokemonEntity
import com.example.pokedexapp.data.local.entities.PokemonTypeEntity
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit


@OptIn(ExperimentalPagingApi::class)
class PokeRemoteMediator constructor(
    private val api: PokeApi,
    private val db: AppDatabase
): RemoteMediator<Int,PokemonEntity>() {

    override suspend fun initialize(): InitializeAction {

        return InitializeAction.SKIP_INITIAL_REFRESH

//        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
//        return if (System.currentTimeMillis() - db.lastUpdated() <= cacheTimeout)
//        {
//            // Cached data is up-to-date, so there is no need to re-fetch
//            // from the network.
//            InitializeAction.SKIP_INITIAL_REFRESH
//        } else {
//            // Need to refresh cached data from network; returning
//            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
//            // APPEND and PREPEND from running until REFRESH succeeds.
//            InitializeAction.LAUNCH_INITIAL_REFRESH
//        }
    }


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(endOfPaginationReached = true)
                    lastItem.page + 1
                }
            } ?: 1


            val offset = (loadKey - 1) * 20

            val pokemonsResponse = api.getPokemons(offset,20)
            val pokemonResults = pokemonsResponse.body()?.results

            if(!pokemonsResponse.isSuccessful||pokemonResults==null){
                return MediatorResult.Error(Throwable(pokemonsResponse.message()))
            }

            val dao = db.pokemonDao()

            if (loadType == LoadType.REFRESH) {
               dao.deleteAllPokemons()
            }

            pokemonResults.forEach { pokemonDto->
                val id = pokemonDto.url.split("/").let {
                    it[it.size - 2].toIntOrNull() ?: 1
                }

                val detailResponse = api.getPokemonDetail(id)
                val detailResult = detailResponse.body()

                if(!detailResponse.isSuccessful||detailResult==null){
                    return MediatorResult.Error(Throwable(detailResponse.message()))
                }

                val speciesResponse = api.getPokemonSpecies(id)
                val speciesResult = speciesResponse.body()

                if(!speciesResponse.isSuccessful||speciesResult==null){
                    return MediatorResult.Error(Throwable(speciesResponse.message()))
                }


                var hp = 0
                var attack = 0
                var defence = 0
                var specialAttack = 0
                var specialDefense = 0
                var speed = 0

                detailResult.stats.forEach {
                    when(it.stat.name){
                        "hp" -> hp = it.base_stat
                        "attack" -> attack = it.base_stat
                        "defense" -> defence = it.base_stat
                        "special-attack" -> specialAttack = it.base_stat
                        "special-defense" -> specialDefense = it.base_stat
                        "speed" -> speed = it.base_stat
                    }
                }


                val pokemonEntity = PokemonEntity(
                    id = id,
                    name = pokemonDto.name,
                    imageUrl = detailResult.sprites.other.officialArtwork.front_default,
                    height = detailResult.height,
                    weight = detailResult.weight,
                    colorName = speciesResult.color.name,
                    flavorText = speciesResult.flavor_text_entries.firstOrNull()?.flavor_text ?: "",
                    hp = hp,
                    attack = attack,
                    defence = defence,
                    specialAttack = specialAttack,
                    specialDefense = specialDefense,
                    speed = speed,
                    page = loadKey
                )

                val typeEntities = detailResult.types.map {
                    PokemonTypeEntity(
                        id = null,
                        pokemonId = id,
                        name = it.type.name,
                        pos = it.slot
                    )
                }

                val moveEntities = detailResult.moves.take(2).mapIndexed { index, moveDto ->
                    MovesEntity(
                        id = null,
                        pokemonId = id,
                        name = moveDto.move.name,
                        pos = index + 1
                    )
                }

                db.withTransaction {
                    dao.insertPokemon(pokemonEntity)
                    dao.insertMoves(moveEntities)
                    dao.insertTypes(typeEntities)
                }
            }


            MediatorResult.Success(
                endOfPaginationReached = pokemonsResponse.body()?.next == null
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }

    }
}