package com.example.pokedexapp.data.mapper

import com.example.pokedexapp.data.local.entities.MovesEntity
import com.example.pokedexapp.data.local.entities.PokemonEntity
import com.example.pokedexapp.data.local.entities.PokemonTypeEntity
import com.example.pokedexapp.data.remote.dto.pokemon_graphql.Data
import com.example.pokedexapp.data.remote.dto.pokemon_graphql.PokemonV2Pokemon
import com.example.pokedexapp.data.remote.dto.pokemon_graphql.PokemonV2Pokemonmove
import com.example.pokedexapp.data.remote.dto.pokemon_graphql.PokemonV2Pokemonstat
import com.example.pokedexapp.data.remote.dto.pokemon_graphql.PokemonV2Pokemontype
import com.example.pokedexapp.data.remote.services.PokeApiResponse
import com.example.pokedexapp.data.utils.StatsInfo
import com.example.pokedexapp.data.utils.StatsUtil


fun PokemonV2Pokemonstat.toStatsInfo(): StatsInfo{
    return StatsInfo(
        name = pokemon_v2_stat.name,
        value = base_stat
    )
}

fun PokemonV2Pokemon.toPokemonEntity(
    remoteKey: String
): PokemonEntity{

    val statsResult = StatsUtil.from(pokemon_v2_pokemonstats.map { it.toStatsInfo() })

    return PokemonEntity(
        id = null,
        pokemonId = id,
        name = name,
        weight = weight,
        height = height,
        remoteKey = remoteKey,
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png",
        flavorText = pokemon_v2_pokemonspecy.pokemon_v2_pokemonspeciesflavortexts.firstOrNull()?.flavor_text ?: "",
        colorName = pokemon_v2_pokemonspecy.pokemon_v2_pokemoncolor.name,
        speed = statsResult.speed,
        hp = statsResult.hp,
        specialDefense = statsResult.specialDefense,
        specialAttack = statsResult.specialAttack,
        defence = statsResult.defence,
        attack = statsResult.attack
    )
}

fun PokemonV2Pokemontype.toPokemonType(
    pokemonId: Int
): PokemonTypeEntity{
    return PokemonTypeEntity(
        id = null,
        pokemonId = pokemonId,
        name = pokemon_v2_type.name,
        pos = slot
    )
}

fun PokemonV2Pokemonmove.toPokemonMove(
    pos: Int,
    pokemonId: Int
): MovesEntity{
    return MovesEntity(
        id = null,
        name = pokemon_v2_move.name,
        pos = pos,
        pokemonId = pokemonId
    )
}
