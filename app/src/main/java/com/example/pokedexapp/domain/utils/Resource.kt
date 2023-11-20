package com.example.pokedexapp.domain.utils

sealed class Resource<T> {

    data class Success<T>(val value: T): Resource<T>()

    data class Error<T>(val message: String?, val value: T? = null): Resource<T>()

}