package com.example.pokedexapp.data.repo

import com.example.pokedexapp.data.mapper.toPokemon
import com.example.pokedexapp.data.remote.PokeApi
import com.example.pokedexapp.domain.models.Pokemon
import com.example.pokedexapp.domain.repo.PokemonRepo
import com.example.pokedexapp.domain.utils.Resource
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class PokemonRepoImpl @Inject constructor(
    private val api: PokeApi
): PokemonRepo {
    override suspend fun getPokemons(): Resource<List<Pokemon>> {
        try {
            val response = api.getPokemons(0,10)
            val result = response.body()
            if(response.isSuccessful && result != null){
                val models = result.results.map { it.toPokemon() }
                return Resource.Success(models)
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


}