package com.example.pokedexapp.data.mapper

import com.example.pokedexapp.data.remote.dto.PokemonDto
import com.example.pokedexapp.domain.models.Pokemon


fun PokemonDto.toPokemon(): Pokemon{
    return Pokemon(
        name, url
    )
}