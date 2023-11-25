package com.example.pokedexapp.data.remote.dto.pokemon_graphql

data class PokemonV2Pokemon(
    val height: Int,
    val id: Int,
    val name: String,
    val weight: Int,
    val pokemon_v2_pokemonmoves: List<PokemonV2Pokemonmove>,
    val pokemon_v2_pokemonspecy: PokemonV2PokemonspecyX,
    val pokemon_v2_pokemonstats: List<PokemonV2Pokemonstat>,
    val pokemon_v2_pokemontypes: List<PokemonV2Pokemontype>
)