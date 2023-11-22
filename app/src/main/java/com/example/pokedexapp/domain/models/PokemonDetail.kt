package com.example.pokedexapp.domain.models

import com.example.pokedexapp.domain.utils.Colors

data class PokemonDetail(
    val pokemon: Pokemon,
    val moves: List<PokemonMove>,
    val types: List<PokemonType>
){

    val baseColor: Int get() = Colors.getColor(types.firstOrNull()?.name)
}
