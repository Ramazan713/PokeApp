package com.example.pokedexapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "remote_keys"
)
data class RemoteKeyEntity(
    @PrimaryKey(autoGenerate = false)
    val label: String,
    val nextKey: Int?
)
