package com.example.pokedexapp.domain.models

import com.example.pokedexapp.domain.extensions.fillWith

data class Pokemon(
    val pokemonId: Int,
    val name: String,
    val imageUrl: String,
    val height: Int,
    val weight: Int,
    val colorName: String,
    val flavorText: String,
    val hp: Int,
    val attack: Int,
    val defence: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int
){

    val heightInMetre: Float get() = height / 10f
    val weightInKg get() = weight / 10f

    val idWithHash: String get() = getIdWithHash(pokemonId)

    companion object{
        fun getIdWithHash(id: Int) = "#${id.fillWith(3)}"
    }

}
