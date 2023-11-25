package com.example.pokedexapp.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Pokemons",
    indices = [Index("pokemonId", unique = true)]
)
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    val pokemonId: Int,
    val name: String,
    val page: Int,
    val imageUrl: String,
    val height: Int,
    val weight: Int,
    val colorName: String,
    val flavorText: String,
    val hp: Int,
    val attack: Int,
    val defence: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int
)
