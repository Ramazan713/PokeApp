package com.example.pokedexapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pokemons")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
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
