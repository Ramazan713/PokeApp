package com.example.pokedexapp.data.mapper

import com.example.pokedexapp.data.local.entities.PokemonTypeEntity
import com.example.pokedexapp.data.remote.dto.pokemon_detail.TypeDto
import com.example.pokedexapp.domain.models.PokemonType


fun PokemonTypeEntity.toPokemonType(): PokemonType {
    return PokemonType(
        id, pokemonId, name, pos
    )
}


fun TypeDto.toPokemonTypeEntity(pokemonId: Int): PokemonTypeEntity{
    return PokemonTypeEntity(
        id = null,
        pokemonId = pokemonId,
        name = type.name,
        pos = slot
    )
}