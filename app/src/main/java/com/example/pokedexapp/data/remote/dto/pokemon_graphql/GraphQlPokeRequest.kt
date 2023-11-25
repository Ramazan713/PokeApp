package com.example.pokedexapp.data.remote.dto.pokemon_graphql

data class GraphQlPokeRequest(
    val query: String,
    val operationName: String,
    val variables: String? = null,
)
