package com.example.pokedexapp.data.remote.dto.pokemon_detail

import com.google.gson.annotations.SerializedName

data class OtherDto(
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtworkDto
)