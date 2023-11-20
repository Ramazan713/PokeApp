package com.example.pokedexapp.data.remote.dto.pokemon_species

data class PokemonSpeciesResponseDto(
    val color: ColorDto,
    val flavor_text_entries: List<FlavorTextEntryDto>,
)