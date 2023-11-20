package com.example.pokedexapp.domain.repo

import com.example.pokedexapp.domain.models.Pokemon
import com.example.pokedexapp.domain.utils.Resource

interface PokemonRepo {

    suspend fun getPokemons(): Resource<List<Pokemon>>
}