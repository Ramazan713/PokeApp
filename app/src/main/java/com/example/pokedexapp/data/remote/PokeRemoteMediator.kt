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
import com.example.pokedexapp.data.remote.services.PokeApiServiceHelper
import com.example.pokedexapp.domain.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
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
): RemoteMediator<Int,PokemonEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        val currentTimeMillis = System.currentTimeMillis()
        val lastSavedTimeMillis = sharedPreferences.getLong("remoteSavedTime",0)
        if(currentTimeMillis - lastSavedTimeMillis <= cacheTimeout){
            return InitializeAction.SKIP_INITIAL_REFRESH
        }
        sharedPreferences.edit().putLong("remoteSavedTime",currentTimeMillis).apply()
        return InitializeAction.LAUNCH_INITIAL_REFRESH
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
                    val lastItem = db.pokemonDao().getLastPage() ?: return MediatorResult.Success(endOfPaginationReached = true)
                    lastItem + 1
                }
            } ?: 1

            val dao = db.pokemonDao()
            var lastPage = loadKey


            if (loadType == LoadType.REFRESH) {
                dao.deleteAllPokemons()
                lastPage += initExtraPageCount
            }
            var lastEndOfPaginationReached = false


            (loadKey..lastPage).forEach { currentPage->
                val response = pokeApiServiceHelper.getPokeEntities(currentPage,pageSize)
                when(response){
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