package com.example.pokedexapp.data.di

import com.example.pokedexapp.data.remote.PokeApi
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
    fun providePokemonRepo(api: PokeApi): PokemonRepo =
        PokemonRepoImpl(api)
}