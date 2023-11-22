package com.example.pokedexapp.data.mapper

import com.example.pokedexapp.data.local.entities.relation.PokemonDetailRelation
import com.example.pokedexapp.domain.models.PokemonDetail

fun PokemonDetailRelation.toPokemonDetail(): PokemonDetail {
    return PokemonDetail(
        pokemon = pokemon.toPokemon(),
        moves = moves.map { it.toPokemonMove() },
        types = types.map { it.toPokemonType() }
    )
}