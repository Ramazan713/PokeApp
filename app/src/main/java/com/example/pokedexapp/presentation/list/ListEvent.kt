package com.example.pokedexapp.presentation.list

import com.example.pokedexapp.domain.enums.OrderEnum

sealed interface ListEvent {

    data class Search(val query: String): ListEvent

    data class SortBy(val sortEnum: OrderEnum): ListEvent
}