package com.example.pokedexapp.data.remote.dto.pokemon_detail


data class PokemonDetailResponseDto(
    val height: Int,
    val id: Int,
    val moves: List<MoveDto>,
    val name: String,
    val order: Int,
    val species: SpeciesDto,
    val sprites: SpritesDto,
    val stats: List<StatDto>,
    val types: List<TypeDto>,
    val weight: Int
)

