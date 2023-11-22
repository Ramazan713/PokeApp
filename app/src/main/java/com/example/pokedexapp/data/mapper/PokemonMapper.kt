package com.example.pokedexapp.data.mapper

import com.example.pokedexapp.data.local.entities.PokemonEntity
import com.example.pokedexapp.domain.models.Pokemon
import com.example.pokedexapp.domain.models.PokemonPart


fun PokemonEntity.toPokemonPart(): PokemonPart {
    return PokemonPart(
        id, name, imageUrl
    )
}

fun PokemonEntity.toPokemon(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        imageUrl = imageUrl,
        height = height,
        weight = weight,
        colorName = colorName,
        flavorText = flavorText,
        hp = hp,
        attack = attack,
        defence = defence,
        specialAttack = specialAttack,
        specialDefense = specialDefense,
        speed = speed
    )
}