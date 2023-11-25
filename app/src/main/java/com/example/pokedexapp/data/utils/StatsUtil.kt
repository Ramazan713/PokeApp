package com.example.pokedexapp.data.utils


object StatsUtil {

    fun from(stats: List<StatsInfo>): StatsResult{
        var hp = 0
        var attack = 0
        var defence = 0
        var specialAttack = 0
        var specialDefense = 0
        var speed = 0

        stats.forEach {
            when(it.name){
                "hp" -> hp = it.value
                "attack" -> attack = it.value
                "defense" -> defence = it.value
                "special-attack" -> specialAttack = it.value
                "special-defense" -> specialDefense = it.value
                "speed" -> speed = it.value
            }
        }

        return StatsResult(
            hp, attack, defence, specialAttack, specialDefense, speed
        )
    }

}

data class StatsInfo(
    val name: String,
    val value: Int
)

data class StatsResult(
    val hp: Int,
    val attack: Int,
    val defence: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int
)