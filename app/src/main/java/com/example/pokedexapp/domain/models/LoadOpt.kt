package com.example.pokedexapp.domain.models

import com.example.pokedexapp.domain.enums.OrderEnum

data class LoadOpt(
    val query: String = "",
    val orderEnum: OrderEnum = OrderEnum.Number
)
