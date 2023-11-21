package com.example.pokedexapp.data.mapper

import com.example.pokedexapp.data.local.entities.PokemonEntity
import com.example.pokedexapp.data.remote.dto.PokemonDto
import com.example.pokedexapp.domain.models.Pokemon


fun PokemonEntity.toPokemon(): Pokemon {
    return Pokemon(
        id, name, imageUrl
    )
}