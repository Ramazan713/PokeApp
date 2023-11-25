package com.example.pokedexapp.data.di

import android.content.SharedPreferences
import com.example.pokedexapp.data.local.AppDatabase
import com.example.pokedexapp.data.remote.services.PokeApiServiceHelper
import com.example.pokedexapp.data.repo.PokemonRepoImpl
import com.example.pokedexapp.domain.repo.PokemonRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepoModule {


    @Provides
    @Singleton
    fun providePokemonRepo(
        apiHelper: PokeApiServiceHelper,
        db: AppDatabase,
        sharedPreferences: SharedPreferences,
    ): PokemonRepo =
        PokemonRepoImpl(
            apiHelper = apiHelper,
            db = db,
            sharedPreferences = sharedPreferences,
        )
}