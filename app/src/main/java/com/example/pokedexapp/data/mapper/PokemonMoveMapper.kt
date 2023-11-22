package com.example.pokedexapp.data.mapper

import com.example.pokedexapp.data.local.entities.MovesEntity
import com.example.pokedexapp.data.remote.dto.pokemon_detail.MoveDto
import com.example.pokedexapp.domain.models.PokemonMove


fun MovesEntity.toPokemonMove(): PokemonMove{
    return PokemonMove(
        id, pokemonId, name, pos
    )
}


fun List<MoveDto>.toMovesEntity(pokemonId: Int): List<MovesEntity>{
    return take(2).mapIndexed { index, moveDto ->
        MovesEntity(
            id = null,
            pokemonId = pokemonId,
            name = moveDto.move.name,
            pos = index + 1
        )
    }
}
