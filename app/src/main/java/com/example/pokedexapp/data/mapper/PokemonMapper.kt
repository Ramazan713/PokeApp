package com.example.pokedexapp.data.mapper

import com.example.pokedexapp.data.local.entities.PokemonEntity
import com.example.pokedexapp.data.remote.dto.PokemonDto
import com.example.pokedexapp.data.remote.dto.PokemonResponseDto
import com.example.pokedexapp.data.remote.dto.pokemon_detail.PokemonDetailResponseDto
import com.example.pokedexapp.data.remote.dto.pokemon_detail.StatDto
import com.example.pokedexapp.data.remote.dto.pokemon_species.PokemonSpeciesResponseDto
import com.example.pokedexapp.data.utils.StatsInfo
import com.example.pokedexapp.data.utils.StatsUtil
import com.example.pokedexapp.domain.models.Pokemon
import com.example.pokedexapp.domain.models.PokemonPart

fun StatDto.toStatsInfo(): StatsInfo{
    return StatsInfo(
        name = stat.name,
        value = base_stat
    )
}


fun PokemonDto.toPokemonEntity(
    id: Int,
    remoteKey: String,
    detailResult: PokemonDetailResponseDto,
    speciesResult: PokemonSpeciesResponseDto
): PokemonEntity{

    val stats = StatsUtil.from(detailResult.stats.map { it.toStatsInfo() })

    return PokemonEntity(
        id = null,
        pokemonId = id,
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
        remoteKey = remoteKey
    )
}



fun PokemonEntity.toPokemonPart(): PokemonPart {
    return PokemonPart(
        pokemonId, name, imageUrl
    )
}

fun PokemonEntity.toPokemon(): Pokemon {
    return Pokemon(
        pokemonId = pokemonId,
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