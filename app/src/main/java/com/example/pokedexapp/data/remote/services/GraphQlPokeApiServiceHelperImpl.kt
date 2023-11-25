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
import com.example.pokedexapp.domain.enums.OrderEnum
import com.example.pokedexapp.domain.models.LoadOpt
import com.example.pokedexapp.domain.utils.Resource
import javax.inject.Inject

class GraphQlPokeApiServiceHelperImpl @Inject constructor(
    private val api: GraphQlPokeApi
): PokeApiServiceHelper {

    override suspend fun getPokeEntities(loadOpt: LoadOpt, page: Int, pageSize: Int): Resource<PokeApiResponse> {
        return try {
            val offset = (page - 1) * pageSize

            val response = api.getPokeData(getRequest(loadOpt, offset, pageSize))
            val responseBody = response.body()

            if(!response.isSuccessful || responseBody==null){
                return Resource.Error(response.message())
            }
            val dataArr = mutableListOf<PokeApiResponseData>()


            responseBody.data.pokemon_v2_pokemonspecies.forEach {rs->
                rs.pokemon_v2_pokemons.forEach {pokemonData->

                    val pokemonEntity = pokemonData.toPokemonEntity(loadOpt.remoteKeyLabel)

                    val moveEntities = pokemonData.pokemon_v2_pokemonmoves.mapIndexed { index, it -> it.toPokemonMove(index + 1,0) }
                    val typeEntities = pokemonData.pokemon_v2_pokemontypes.map { it.toPokemonType(0) }

                    val data = PokeApiResponseData(
                        pokemonEntity, typeEntities, moveEntities
                    )
                    dataArr.add(data)
                }
            }


            return Resource.Success(
                PokeApiResponse(
                    data = dataArr,
                    endOfPaginationReached = dataArr.isEmpty()
                )
            )
        }catch (e: Exception){
            return Resource.Error(e.localizedMessage)
        }
    }



    private fun getRequest(loadOpt: LoadOpt, offset: Int, pageSize: Int): GraphQlPokeRequest{
        if(loadOpt.query.isNotBlank()){
            return when(loadOpt.orderEnum){
                OrderEnum.Number -> {
                    searchRequestOrderByNumber(loadOpt.query,offset, pageSize)
                }

                OrderEnum.Name -> {
                    searchRequestOrderByName(loadOpt.query,offset, pageSize)
                }
            }
        }
        return when(loadOpt.orderEnum){
            OrderEnum.Number -> {
                requestOrderByNumber(offset, pageSize)
            }
            OrderEnum.Name -> {
                requestOrderByName(offset, pageSize)
            }
        }
    }


    private fun searchRequestOrderByName(query: String, offset: Int, pageSize: Int): GraphQlPokeRequest{
        return GraphQlPokeRequest(
            operationName = "samplePokeAPIQuery",
            query = """
                query samplePokeAPIQuery {
                  pokemon_v2_pokemonspecies(order_by: { name: asc}, limit: $pageSize, offset: $offset, where: {name: {_ilike: "%$query%"}}) {
                   	...getPokemons
                  }
                }
                ${getFragments()}
            """.trimIndent()
        )
    }

    private fun searchRequestOrderByNumber(query: String, offset: Int, pageSize: Int): GraphQlPokeRequest{
        return GraphQlPokeRequest(
            operationName = "samplePokeAPIQuery",
            query = """
                query samplePokeAPIQuery {
                  pokemon_v2_pokemonspecies(order_by: { id: asc}, limit: $pageSize, offset: $offset, where: {name: {_ilike: "%$query%"}}) {
                   	...getPokemons
                  }
                }
                ${getFragments()}
            """.trimIndent()
        )
    }

    private fun requestOrderByNumber(offset: Int, pageSize: Int): GraphQlPokeRequest{
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

    private fun requestOrderByName(offset: Int, pageSize: Int): GraphQlPokeRequest{
        return GraphQlPokeRequest(
            operationName = "samplePokeAPIQuery",
            query = """
                query samplePokeAPIQuery {
                  pokemon_v2_pokemonspecies(order_by: { name: asc}, limit: $pageSize, offset: $offset) {
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