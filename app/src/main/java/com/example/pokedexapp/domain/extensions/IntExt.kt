package com.example.pokedexapp.domain.extensions

fun Int.fillWith(length: Int = 3, padChar: Char = '0'): String{
    return this.toString().padStart(length,padChar)
}