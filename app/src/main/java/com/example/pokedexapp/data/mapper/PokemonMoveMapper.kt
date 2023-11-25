package com.example.pokedexapp.data.mapper

import com.example.pokedexapp.data.local.entities.MovesEntity
import com.example.pokedexapp.domain.models.PokemonMove


fun MovesEntity.toPokemonMove(): PokemonMove{
    return PokemonMove(
        id, pokemonId, name, pos
    )
}