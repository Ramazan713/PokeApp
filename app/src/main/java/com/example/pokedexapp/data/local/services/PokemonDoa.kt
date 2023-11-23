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
    suspend fun insertMoves(moves: List<MovesEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTypes(types: List<PokemonTypeEntity>)

    @Query("select * from pokemons")
    fun getPokemonDetails():PagingSource<Int,PokemonDetailRelation>

    @Query("select * from pokemons order by id")
    fun getPokemonsOrderById(): PagingSource<Int,PokemonEntity>

    @Query("select * from pokemons order by name")
    fun getPokemonsOrderByName(): PagingSource<Int,PokemonEntity>

    @Query("""
        select * from pokemons where 
        id like :query or
        name like :query
        order by id
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

    @Query("select count(*) from pokemons where id < :id")
    suspend fun getPokemonPositionById(id: Int): Int?


    @Query("delete from pokemons")
    suspend fun deleteAllPokemons()

}