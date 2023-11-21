package com.example.pokedexapp.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import androidx.paging.map
import com.example.pokedexapp.data.local.AppDatabase
import com.example.pokedexapp.data.mapper.toPokemon
import com.example.pokedexapp.data.remote.PokeApi
import com.example.pokedexapp.data.remote.PokeRemoteMediator
import com.example.pokedexapp.domain.models.Pokemon
import com.example.pokedexapp.domain.repo.PokemonRepo
import com.example.pokedexapp.domain.utils.Resource
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class PokemonRepoImpl @Inject constructor(
    private val api: PokeApi,
    private val db: AppDatabase
): PokemonRepo {
    override suspend fun getPokemons(): Resource<List<Pokemon>> {
        try {
            val response = api.getPokemons(0,10)
            val result = response.body()
            if(response.isSuccessful && result != null){
//                val models = result.results.map { it.toPokemon() }
                return Resource.Success(listOf())
            }
            return Resource.Error(response.errorBody()?.string() ?: "Error")
        }
        catch (e: CancellationException){
            throw e
        }
        catch (e: Exception){
            return Resource.Error(e.localizedMessage)
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getPokemonsPaging(): LiveData<PagingData<Pokemon>> {

        val pager = Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            initialKey = 1,
            remoteMediator = PokeRemoteMediator(api,db),
            pagingSourceFactory = {
                db.pokemonDao().getPokemons()
            }
        )

        return pager.liveData.map {pagingData->
            pagingData.map { it.toPokemon() }
        }
    }


}