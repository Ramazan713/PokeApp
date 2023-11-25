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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val pokemonRepo: PokemonRepo,
): ViewModel(){

    private val mutableOpt = MutableLiveData<LoadOpt>()

    val pagingData = mutableOpt.switchMap { opt->
        pokemonRepo.getPokemonDetailsPaging(opt)
    }.cachedIn(viewModelScope)

    fun loadData(query: String, orderValueEnum: Int){
        val orderEnum = OrderEnum.from(orderValueEnum)
        val opt = LoadOpt(query, orderEnum)
        mutableOpt.value = opt
    }
}