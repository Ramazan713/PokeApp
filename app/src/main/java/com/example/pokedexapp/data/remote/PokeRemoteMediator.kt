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
import com.example.pokedexapp.data.mapper.toMovesEntity
import com.example.pokedexapp.data.mapper.toPokemonEntity
import com.example.pokedexapp.data.mapper.toPokemonTypeEntity
import com.example.pokedexapp.data.remote.services.PokeApiServiceHelper
import com.example.pokedexapp.domain.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit


@OptIn(ExperimentalPagingApi::class)
class PokeRemoteMediator constructor(
    private val db: AppDatabase,
    private val pageSize: Int,
    private val pokeApiServiceHelper: PokeApiServiceHelper
): RemoteMediator<Int,PokemonEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
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

            val dao = db.pokemonDao()

            if (loadType == LoadType.REFRESH) {
               dao.deleteAllPokemons()
            }

            when(val response = pokeApiServiceHelper.getPokeEntities(loadKey,pageSize)){
                is Resource.Error -> {
                    return MediatorResult.Error(Throwable(response.message))
                }
                is Resource.Success -> {
                    val value = response.value
                    db.withTransaction {
                        dao.insertPokemons(value.pokemonEntities)
                        dao.insertMoves(value.moveEntities)
                        dao.insertTypes(value.typeEntities)
                    }

                    MediatorResult.Success(
                        endOfPaginationReached = value.endOfPaginationReached
                    )
                }
            }

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }

    }
}