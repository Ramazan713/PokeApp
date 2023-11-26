package com.example.pokedexapp.domain.enums

enum class OrderEnum(val valueEnum: Int, val title: String) {
    Number(1, title = "Number"),
    Name(2, title = "Name");

    companion object{
        fun from(valueEnum: Int): OrderEnum{
            return when(valueEnum){
                OrderEnum.Number.valueEnum -> OrderEnum.Number
                OrderEnum.Name.valueEnum -> OrderEnum.Name
                else -> Number
            }
        }
    }
}