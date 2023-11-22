package com.example.pokedexapp.domain.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.pokedexapp.domain.models.PokemonDetail
import com.example.pokedexapp.domain.models.PokemonPart
import com.example.pokedexapp.domain.utils.Resource

interface PokemonRepo {

    suspend fun getPokemons(): Resource<List<PokemonPart>>

    fun getPokemonsPaging(): LiveData<PagingData<PokemonPart>>

    suspend fun getPokemonDetail(id: Int): PokemonDetail?

}