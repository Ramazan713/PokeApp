package com.example.pokedexapp.domain.models

data class Pokemon(
    val id: Int,
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

}
