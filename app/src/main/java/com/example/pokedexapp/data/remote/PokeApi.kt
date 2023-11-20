package com.example.pokedexapp.data.remote
import com.example.pokedexapp.data.remote.dto.PokemonResponseDto
import com.example.pokedexapp.data.remote.dto.pokemon_detail.PokemonDetailResponseDto
import com.example.pokedexapp.data.remote.dto.pokemon_species.PokemonSpeciesResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {


    @GET("/pokemon")
    suspend fun getPokemons(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonResponseDto


    @GET("/pokemon/{id}")
    suspend fun getPokemonDetail(
        @Path("id") id: Int
    ): PokemonDetailResponseDto


    @GET("/pokemon-species/{id}")
    suspend fun getPokemonSpecies(
        @Path("id") id: Int
    ): PokemonSpeciesResponseDto

}