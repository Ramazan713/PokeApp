package com.example.pokedexapp.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "PokemonTypes",
    foreignKeys = [
        ForeignKey(
            entity = PokemonEntity::class,
            childColumns = ["pokemonId"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PokemonTypeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val pokemonId: Int,
    val name: String,
    val pos: Int
)
