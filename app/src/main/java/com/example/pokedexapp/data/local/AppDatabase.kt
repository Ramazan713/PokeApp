package com.example.pokedexapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokedexapp.data.local.entities.MovesEntity
import com.example.pokedexapp.data.local.entities.PokemonEntity
import com.example.pokedexapp.data.local.entities.PokemonTypeEntity
import com.example.pokedexapp.data.local.entities.RemoteKeyEntity
import com.example.pokedexapp.data.local.services.PokemonDoa
import com.example.pokedexapp.data.local.services.RemoteKeyDao

@Database(
    version = 1,
    entities = [
        PokemonEntity::class, MovesEntity::class, PokemonTypeEntity::class,
        RemoteKeyEntity::class
    ]
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun pokemonDao(): PokemonDoa

    abstract fun remoteKeyDao(): RemoteKeyDao
}