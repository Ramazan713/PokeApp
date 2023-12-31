package com.example.pokedexapp.data.mapper

import com.example.pokedexapp.data.local.entities.PokemonEntity
import com.example.pokedexapp.data.remote.dto.PokemonDto
import com.example.pokedexapp.data.remote.dto.PokemonResponseDto
import com.example.pokedexapp.data.remote.dto.pokemon_detail.PokemonDetailResponseDto
import com.example.pokedexapp.data.remote.dto.pokemon_species.PokemonSpeciesResponseDto
import com.example.pokedexapp.data.utils.StatsUtil
import com.example.pokedexapp.domain.models.Pokemon
import com.example.pokedexapp.domain.models.PokemonPart


fun PokemonDto.toPokemonEntity(
    id: Int,
    page: Int,
    detailResult: PokemonDetailResponseDto,
    speciesResult: PokemonSpeciesResponseDto
): PokemonEntity{

    val stats = StatsUtil.from(detailResult.stats)

    return PokemonEntity(
        id = id,
        name = name,
        imageUrl = detailResult.sprites.other.officialArtwork.front_default,
        height = detailResult.height,
        weight = detailResult.weight,
        colorName = speciesResult.color.name,
        flavorText = speciesResult.flavor_text_entries.firstOrNull()?.flavor_text ?: "",
        hp = stats.hp,
        attack = stats.attack,
        defence = stats.defence,
        specialAttack = stats.specialAttack,
        specialDefense = stats.specialDefense,
        speed = stats.speed,
        page = page
    )
}



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