package com.example.pokedexapp.data.remote.services

import com.example.pokedexapp.data.local.entities.MovesEntity
import com.example.pokedexapp.data.local.entities.PokemonEntity
import com.example.pokedexapp.data.local.entities.PokemonTypeEntity
import com.example.pokedexapp.domain.models.LoadOpt
import com.example.pokedexapp.domain.utils.Resource

interface PokeApiServiceHelper {


    suspend fun getPokeEntities(loadOpt: LoadOpt,page: Int, pageSize: Int): Resource<PokeApiResponse>
}


data class PokeApiResponseData(
    val pokemonEntity: PokemonEntity,
    val typeEntities: List<PokemonTypeEntity>,
    val moveEntities: List<MovesEntity>,
)
data class PokeApiResponse(
    val data: List<PokeApiResponseData>,
    val endOfPaginationReached: Boolean
)