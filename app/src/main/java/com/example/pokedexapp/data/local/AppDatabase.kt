package com.example.pokedexapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokedexapp.data.local.entities.MovesEntity
import com.example.pokedexapp.data.local.entities.PokemonEntity
import com.example.pokedexapp.data.local.entities.PokemonTypeEntity
import com.example.pokedexapp.data.local.services.PokemonDoa

@Database(
    version = 1,
    entities = [
        PokemonEntity::class, MovesEntity::class, PokemonTypeEntity::class
    ]
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun pokemonDao(): PokemonDoa
}