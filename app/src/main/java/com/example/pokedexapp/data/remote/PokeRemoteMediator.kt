package com.example.pokedexapp.data.remote

import android.content.SharedPreferences
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.pokedexapp.data.local.AppDatabase
import com.example.pokedexapp.data.local.entities.PokemonEntity
import com.example.pokedexapp.data.local.entities.RemoteKeyEntity
import com.example.pokedexapp.data.remote.services.PokeApiServiceHelper
import com.example.pokedexapp.domain.enums.OrderEnum
import com.example.pokedexapp.domain.models.LoadOpt
import com.example.pokedexapp.domain.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit


@OptIn(ExperimentalPagingApi::class)
class PokeRemoteMediator constructor(
    private val db: AppDatabase,
    private val pageSize: Int,
    private val pokeApiServiceHelper: PokeApiServiceHelper,
    private val sharedPreferences: SharedPreferences,
    private val initExtraPageCount: Int,
    private val loadOpt: LoadOpt
): RemoteMediator<Int,PokemonEntity>() {

    override suspend fun initialize(): InitializeAction {
        val key = loadOpt.remoteKeyLabel + "remoteSavedTime"

        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        val currentTimeMillis = System.currentTimeMillis()
        val lastSavedTimeMillis = sharedPreferences.getLong(key,0)
        if(currentTimeMillis - lastSavedTimeMillis <= cacheTimeout){
            return InitializeAction.SKIP_INITIAL_REFRESH
        }
        sharedPreferences.edit().putLong(key,currentTimeMillis).apply()
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        return try {

            val pokemonDao = db.pokemonDao()
            val remoteKeyDao = db.remoteKeyDao()

            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    remoteKeyDao.remoteKeyByQuery(loadOpt.remoteKeyLabel)?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            } ?: 1

            var lastPage = loadKey

            if (loadType == LoadType.REFRESH) {
                pokemonDao.deletePokemonsByRemoteKey(loadOpt.remoteKeyLabel)
                remoteKeyDao.deleteByQuery(loadOpt.remoteKeyLabel)
                lastPage += initExtraPageCount
            }
            var lastEndOfPaginationReached = false


            (loadKey..lastPage).forEach { currentPage->
                when(val response = pokeApiServiceHelper.getPokeEntities(loadOpt, currentPage,pageSize)){
                    is Resource.Error -> {
                        return MediatorResult.Error(Throwable(response.message))
                    }
                    is Resource.Success -> {
                        val value = response.value
                        db.withTransaction {
                            remoteKeyDao.insertOrReplace(RemoteKeyEntity(loadOpt.remoteKeyLabel,if(value.endOfPaginationReached) null else currentPage +1))

                            value.data.forEach { data->
                                val id = pokemonDao.insertPokemon(data.pokemonEntity).toInt()
                                val updatedMoves = data.moveEntities.map { it.copy(pokemonId = id) }
                                val updatedTypes = data.typeEntities.map { it.copy(pokemonId = id) }

                                pokemonDao.insertTypes(updatedTypes)
                                pokemonDao.insertMoves(updatedMoves)
                            }
                        }

                        lastEndOfPaginationReached = value.endOfPaginationReached
                    }
                }
            }
            MediatorResult.Success(
                endOfPaginationReached = lastEndOfPaginationReached
            )

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }

    }
}