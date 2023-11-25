package com.example.pokedexapp.domain.enums

enum class OrderEnum(val valueEnum: Int) {
    Number(1),
    Name(2);

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