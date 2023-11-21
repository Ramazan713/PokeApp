package com.example.pokedexapp.data.local.entities.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.pokedexapp.data.local.entities.MovesEntity
import com.example.pokedexapp.data.local.entities.PokemonEntity
import com.example.pokedexapp.data.local.entities.PokemonTypeEntity


data class PokemonUnitRelation(
    @Embedded
    val pokemon: PokemonEntity,

    @Relation(
        entity = PokemonEntity::class,
        parentColumn = "id",
        entityColumn = "pokemonId"
    )
    val moves: List<MovesEntity>,

    @Relation(
        entity = PokemonEntity::class,
        parentColumn = "id",
        entityColumn = "pokemonId"
    )
    val types: List<PokemonTypeEntity>,
)