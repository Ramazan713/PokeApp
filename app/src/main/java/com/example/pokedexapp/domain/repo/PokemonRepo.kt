package com.example.pokedexapp.domain.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.pokedexapp.domain.models.LoadOpt
import com.example.pokedexapp.domain.models.PokemonDetail
import com.example.pokedexapp.domain.models.PokemonPart
import com.example.pokedexapp.domain.utils.Resource

interface PokemonRepo {

    fun getPokemonDetailsPaging(): LiveData<PagingData<PokemonDetail>>


    fun getPokemonsPaging(opt: LoadOpt): LiveData<PagingData<PokemonPart>>

    fun searchPokemons(opt: LoadOpt): LiveData<PagingData<PokemonPart>>

    suspend fun getPokemonPositionById(id: Int): Int

}