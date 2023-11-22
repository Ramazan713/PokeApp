package com.example.pokedexapp.domain.models

data class PokemonMove(
    val id: Int?,
    val pokemonId: Int,
    val name: String,
    val pos: Int
)
