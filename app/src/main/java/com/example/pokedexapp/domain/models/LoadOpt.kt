package com.example.pokedexapp.domain.models

import com.example.pokedexapp.domain.enums.OrderEnum

data class LoadOpt(
    val query: String = "",
    val orderEnum: OrderEnum = OrderEnum.Number
){

    val remoteKeyLabel = getRemoteKeyLabel(query, orderEnum)


    companion object{
        fun getRemoteKeyLabel(query: String, orderEnum: OrderEnum): String{
            return when{
                query.isNotBlank() -> {
                    "search-${orderEnum.name}-${query}"
                }
                orderEnum == OrderEnum.Number -> {
                    "orderBy-Number"
                }
                orderEnum == OrderEnum.Name -> {
                    "orderBy-Name"
                }
                else -> { "Default"}
            }
        }
    }

}
