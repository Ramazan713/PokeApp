package com.example.pokedexapp.presentation.list

import com.example.pokedexapp.domain.enums.OrderEnum
import com.example.pokedexapp.domain.models.LoadOpt


data class ListState(
    val query: String = "",
    val orderEnum: OrderEnum = OrderEnum.Number
){

    val opt get() = LoadOpt(query, orderEnum)
}
