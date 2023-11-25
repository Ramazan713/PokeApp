package com.example.pokedexapp.data.di

import android.app.Application
import com.example.pokedexapp.data.remote.GraphQlPokeApi
import com.example.pokedexapp.data.remote.PokeApi
import com.example.pokedexapp.data.remote.services.GraphQlPokeApiServiceHelperImpl
import com.example.pokedexapp.data.remote.services.PokeApiServiceHelper
import com.example.pokedexapp.data.remote.services.PokeApiServiceHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {


    @Provides
    fun providePokemonApi(): PokeApi =
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeApi::class.java)


    @Provides
    fun provideGraphQlPokemonApi(): GraphQlPokeApi =
        Retrofit.Builder()
            .baseUrl("https://beta.pokeapi.co/graphql/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GraphQlPokeApi::class.java)


    @Provides
    fun provideApiHelper(api: GraphQlPokeApi): PokeApiServiceHelper =
        GraphQlPokeApiServiceHelperImpl(api)

}