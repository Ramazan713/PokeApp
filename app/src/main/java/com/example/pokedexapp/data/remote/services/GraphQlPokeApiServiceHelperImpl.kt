package com.example.pokedexapp.data.remote.services

import android.util.Log
import com.example.pokedexapp.data.local.entities.MovesEntity
import com.example.pokedexapp.data.local.entities.PokemonEntity
import com.example.pokedexapp.data.local.entities.PokemonTypeEntity
import com.example.pokedexapp.data.mapper.toPokemonEntity
import com.example.pokedexapp.data.mapper.toPokemonMove
import com.example.pokedexapp.data.mapper.toPokemonType
import com.example.pokedexapp.data.remote.GraphQlPokeApi
import com.example.pokedexapp.data.remote.dto.pokemon_graphql.GraphQlPokeRequest
import com.example.pokedexapp.domain.utils.Resource
import javax.inject.Inject

class GraphQlPokeApiServiceHelperImpl @Inject constructor(
    private val api: GraphQlPokeApi
): PokeApiServiceHelper {

    override suspend fun getPokeEntities(page: Int, pageSize: Int): Resource<PokeApiResponse> {
        val offset = (page - 1) * pageSize

        val response = api.getPokeData(getRequest(offset, pageSize))
        val responseBody = response.body()

        if(!response.isSuccessful || responseBody==null){
            return Resource.Error(response.message())
        }
        val pokemonsArr = mutableListOf<PokemonEntity>()
        val typesArr = mutableListOf<PokemonTypeEntity>()
        val movesArr = mutableListOf<MovesEntity>()

        responseBody.data.pokemon_v2_pokemonspecies.forEach {rs->
            rs.pokemon_v2_pokemons.forEach {pokemonData->

                val pokemonEntity = pokemonData.toPokemonEntity(page)

                val moveEntities = pokemonData.pokemon_v2_pokemonmoves.mapIndexed { index, it -> it.toPokemonMove(index + 1,pokemonData.id) }
                val typeEntities = pokemonData.pokemon_v2_pokemontypes.map { it.toPokemonType(pokemonData.id) }

                pokemonsArr.add(pokemonEntity)
                typesArr.addAll(typeEntities)
                movesArr.addAll(moveEntities)
            }
        }


        return Resource.Success(
            PokeApiResponse(
                pokemonEntities = pokemonsArr,
                typeEntities = typesArr,
                moveEntities = movesArr,
                endOfPaginationReached = pokemonsArr.isEmpty()
            )
        )
    }



    private fun getRequest(offset: Int, pageSize: Int): GraphQlPokeRequest{
        return GraphQlPokeRequest(
            operationName = "samplePokeAPIQuery",
            query = """
                query samplePokeAPIQuery {
                  pokemon_v2_pokemonspecies(order_by: { id: asc}, limit: $pageSize, offset: $offset) {
                   	...getPokemons
                  }
                }
                ${getFragments()}
            """.trimIndent()
        )
    }

    private fun getFragments(): String{
        return """
            fragment getPokemons on pokemon_v2_pokemonspecies{
               pokemon_v2_pokemons {
                  id
                  name
                  height
                  weight
                  pokemon_v2_pokemonstats {
                    base_stat
                    pokemon_v2_stat {
                      name
                    }
                  }
                  pokemon_v2_pokemonmoves(limit: 2) {
                    pokemon_v2_move {
                      name
                    }
                  }
                  pokemon_v2_pokemonspecy {
                    pokemon_v2_pokemoncolor {
                      name
                    }
                    pokemon_v2_pokemonspeciesflavortexts(limit: 1) {
                      flavor_text
                    }
                  }
                  pokemon_v2_pokemontypes {
                    pokemon_v2_type {
                      name
                    }
                    slot
                  }
                }
            }
        """.trimIndent()
    }

}