package com.example.pokedexapp.data.utils

import com.example.pokedexapp.data.remote.dto.pokemon_detail.StatDto

object StatsUtil {

    fun from(stats: List<StatDto>): StatsResult{
        var hp = 0
        var attack = 0
        var defence = 0
        var specialAttack = 0
        var specialDefense = 0
        var speed = 0

        stats.forEach {
            when(it.stat.name){
                "hp" -> hp = it.base_stat
                "attack" -> attack = it.base_stat
                "defense" -> defence = it.base_stat
                "special-attack" -> specialAttack = it.base_stat
                "special-defense" -> specialDefense = it.base_stat
                "speed" -> speed = it.base_stat
            }
        }

        return StatsResult(
            hp, attack, defence, specialAttack, specialDefense, speed
        )
    }

}



data class StatsResult(
    val hp: Int,
    val attack: Int,
    val defence: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int
)