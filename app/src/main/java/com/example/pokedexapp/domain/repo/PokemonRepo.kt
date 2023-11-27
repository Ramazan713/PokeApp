package com.example.pokedexapp.domain.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.pokedexapp.domain.models.LoadOpt
import com.example.pokedexapp.domain.models.PokemonDetail
import com.example.pokedexapp.domain.models.PokemonPart
import com.example.pokedexapp.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PokemonRepo {

    fun getPokemonDetailsPaging(opt: LoadOpt): Flow<PagingData<PokemonDetail>>


    fun getPokemonsPaging(opt: LoadOpt): Flow<PagingData<PokemonPart>>
}