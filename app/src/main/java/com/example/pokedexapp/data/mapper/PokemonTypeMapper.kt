package com.example.pokedexapp.data.mapper

import com.example.pokedexapp.data.local.entities.PokemonTypeEntity
import com.example.pokedexapp.domain.models.PokemonType


fun PokemonTypeEntity.toPokemonType(): PokemonType {
    return PokemonType(
        id, pokemonId, name, pos
    )
}
