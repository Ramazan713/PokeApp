package com.example.pokedexapp.data.remote

import com.example.pokedexapp.data.remote.dto.pokemon_graphql.GraphQlPokeRequest
import com.example.pokedexapp.data.remote.dto.pokemon_graphql.response.PokeGraphQlResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GraphQlPokeApi {

    @POST("v1beta")
    suspend fun getPokeData(
        @Body body: GraphQlPokeRequest
    ): Response<PokeGraphQlResponse>

}