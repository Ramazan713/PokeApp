package com.example.pokedexapp.presentation.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.pokedexapp.domain.enums.OrderEnum
import com.example.pokedexapp.domain.models.LoadOpt
import com.example.pokedexapp.domain.models.PokemonDetail
import com.example.pokedexapp.domain.repo.PokemonRepo
import com.example.pokedexapp.domain.use_cases.GetPokemonsPartUseCase
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
    private val pokemonRepo: PokemonRepo,
): ViewModel(){

    private val mutableOpt = MutableStateFlow<LoadOpt?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingData = mutableOpt.filter { it != null }.flatMapLatest { opt ->
        pokemonRepo.getPokemonDetailsPaging(opt!!)
    }.cachedIn(viewModelScope)

    fun loadData(query: String, orderValueEnum: Int){
        viewModelScope.launch {
            val orderEnum = OrderEnum.from(orderValueEnum)
            val opt = LoadOpt(query, orderEnum)
            mutableOpt.update { opt }
        }
    }
}