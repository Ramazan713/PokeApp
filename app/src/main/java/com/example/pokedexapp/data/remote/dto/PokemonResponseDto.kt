package com.example.pokedexapp.data.remote.dto

data class PokemonResponseDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonDto>
)
