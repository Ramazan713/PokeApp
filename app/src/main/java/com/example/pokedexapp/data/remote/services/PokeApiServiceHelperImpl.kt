package com.example.pokedexapp.data.remote.services

import com.example.pokedexapp.data.local.entities.MovesEntity
import com.example.pokedexapp.data.local.entities.PokemonEntity
import com.example.pokedexapp.data.local.entities.PokemonTypeEntity
import com.example.pokedexapp.data.mapper.toMovesEntity
import com.example.pokedexapp.data.mapper.toPokemonEntity
import com.example.pokedexapp.data.mapper.toPokemonTypeEntity
import com.example.pokedexapp.data.remote.PokeApi
import com.example.pokedexapp.domain.utils.Resource
import javax.inject.Inject

class PokeApiServiceHelperImpl @Inject constructor(
    private val api: PokeApi
): PokeApiServiceHelper {

    override suspend fun getPokeEntities(page: Int, pageSize: Int): Resource<PokeApiResponse> {
        val offset = (page - 1) * pageSize

        val pokemonsResponse = api.getPokemons(offset,pageSize)
        val pokemonResults = pokemonsResponse.body()?.results

        if(!pokemonsResponse.isSuccessful||pokemonResults==null){
            return Resource.Error(pokemonsResponse.message())
        }

        val pokemonsArr = mutableListOf<PokemonEntity>()
        val typesArr = mutableListOf<PokemonTypeEntity>()
        val movesArr = mutableListOf<MovesEntity>()

        pokemonResults.forEach { pokemonDto->
            val id = pokemonDto.url.split("/").let {
                it[it.size - 2].toIntOrNull() ?: 1
            }

            val detailResponse = api.getPokemonDetail(id)
            val detailResult = detailResponse.body()

            if(!detailResponse.isSuccessful || detailResult == null){
                return Resource.Error(detailResponse.message())
            }

            val speciesResponse = api.getPokemonSpecies(id)
            val speciesResult = speciesResponse.body()

            if(!speciesResponse.isSuccessful || speciesResult == null){
                return Resource.Error(speciesResponse.message())
            }

            val pokemonEntity = pokemonDto.toPokemonEntity(
                id = id,
                page = page,
                detailResult = detailResult,
                speciesResult = speciesResult
            )

            val typeEntities = detailResult.types.map { it.toPokemonTypeEntity(id)}
            val moveEntities = detailResult.moves.toMovesEntity(id)

            pokemonsArr.add(pokemonEntity)
            typesArr.addAll(typeEntities)
            movesArr.addAll(moveEntities)
        }

        return Resource.Success(
            PokeApiResponse(
                pokemonEntities = pokemonsArr,
                typeEntities = typesArr,
                moveEntities = movesArr,
                endOfPaginationReached = pokemonsResponse.body()?.next == null
            )
        )
    }
}