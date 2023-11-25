package com.example.pokedexapp.data.local.services

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokedexapp.data.local.entities.MovesEntity
import com.example.pokedexapp.data.local.entities.PokemonEntity
import com.example.pokedexapp.data.local.entities.PokemonTypeEntity
import com.example.pokedexapp.data.local.entities.relation.PokemonDetailRelation

@Dao
interface PokemonDoa {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemons(pokemonEntities: List<PokemonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemonEntity: PokemonEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoves(moves: List<MovesEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTypes(types: List<PokemonTypeEntity>)

    @Query("select * from pokemons where remoteKey = :remoteKey order by pokemonId")
    fun getPokemonDetails(
        remoteKey: String
    ):PagingSource<Int,PokemonDetailRelation>

    @Query("select * from pokemons where remoteKey = :remoteKey order by pokemonId")
    fun getPokemonsOrderById(
        remoteKey: String
    ): PagingSource<Int,PokemonEntity>

    @Query("select * from pokemons where remoteKey = :remoteKey order by name")
    fun getPokemonsOrderByName(
        remoteKey: String
    ): PagingSource<Int,PokemonEntity>

    @Query("""
        select * from pokemons where 
        id like :query or
        name like :query
        order by pokemonId
    """)
    fun searchPokemonsOrderById(
        query: String
    ): PagingSource<Int,PokemonEntity>

    @Query("""
        select * from pokemons where 
        id like :query or
        name like :query
        order by name
    """)
    fun searchPokemonsOrderByName(
        query: String
    ): PagingSource<Int,PokemonEntity>


    @Query("delete from pokemons where remoteKey = :remoteKey")
    suspend fun deletePokemonsByRemoteKey(remoteKey: String)

}