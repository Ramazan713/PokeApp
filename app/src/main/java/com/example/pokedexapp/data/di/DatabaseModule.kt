package com.example.pokedexapp.data.di

import android.app.Application
import androidx.room.Room
import com.example.pokedexapp.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase =
        Room.databaseBuilder(application,AppDatabase::class.java,"pokemons.db")
            .build()
}