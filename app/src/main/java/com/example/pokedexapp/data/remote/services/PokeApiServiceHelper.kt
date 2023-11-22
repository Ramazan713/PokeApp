package com.example.pokedexapp.data.remote.services

import com.example.pokedexapp.data.local.entities.MovesEntity
import com.example.pokedexapp.data.local.entities.PokemonEntity
import com.example.pokedexapp.data.local.entities.PokemonTypeEntity
import com.example.pokedexapp.domain.utils.Resource

interface PokeApiServiceHelper {


    suspend fun getPokeEntities(page: Int, pageSize: Int): Resource<PokeApiResponse>
}

data class PokeApiResponse(
    val pokemonEntities: List<PokemonEntity>,
    val typeEntities: List<PokemonTypeEntity>,
    val moveEntities: List<MovesEntity>,
    val endOfPaginationReached: Boolean
)