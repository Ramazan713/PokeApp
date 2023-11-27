package com.example.pokedexapp.presentation.utils

import com.example.pokedexapp.domain.models.Pokemon
import com.example.pokedexapp.domain.models.PokemonDetail
import com.example.pokedexapp.domain.models.PokemonMove
import com.example.pokedexapp.domain.models.PokemonType

object SampleData {

    val pokemon = Pokemon(
        1,"Pokemon X","",75,100,"green",
        "flavor",75,100,200,67,75,98
    )

    val moves = listOf(PokemonMove(1,1,"move 1",1),PokemonMove(2,1,"move 2",2))

    val types = listOf(PokemonType(1,1,"grass",1),PokemonType(2,1,"water",2))

    val pokemonDetail = PokemonDetail(pokemon, moves, types)

}