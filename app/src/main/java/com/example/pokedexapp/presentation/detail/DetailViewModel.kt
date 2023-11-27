package com.example.pokedexapp.presentation.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.pokedexapp.domain.enums.OrderEnum
import com.example.pokedexapp.domain.models.LoadOpt
import com.example.pokedexapp.domain.models.PokemonDetail
import com.example.pokedexapp.domain.repo.PokemonRepo
import com.example.pokedexapp.domain.use_cases.GetPokemonsPartUseCase
import com.example.pokedexapp.presentation.detail.navigation.DetailArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.switchMap
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    pokemonRepo: PokemonRepo,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    val args = DetailArgs(savedStateHandle)

    val pagingData = pokemonRepo.getPokemonDetailsPaging(args.let {args->
        val orderEnum = OrderEnum.from(args.orderEnumValue)
        LoadOpt(args.query, orderEnum)
    }).cachedIn(viewModelScope)
}