package com.example.pokedexapp.presentation.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.pokedexapp.domain.enums.OrderEnum
import com.example.pokedexapp.domain.models.LoadOpt
import com.example.pokedexapp.domain.models.PokemonPart
import com.example.pokedexapp.domain.repo.PokemonRepo
import com.example.pokedexapp.domain.use_cases.GetPokemonsPartUseCase
import com.example.pokedexapp.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.switchMap
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getPokemonsUseCase: GetPokemonsPartUseCase
): ViewModel(){
    private val mutableOpt = MutableStateFlow(LoadOpt())
    val opt: StateFlow<LoadOpt> = mutableOpt

    val sortBy: Flow<OrderEnum> = mutableOpt.map { it.orderEnum }

    val pagingData = mutableOpt.flatMapLatest { opt ->
        getPokemonsUseCase(opt)
    }.cachedIn(viewModelScope)

    private var searchJob: Job? = null


    fun onEvent(event: ListEvent){
        when(event){
            is ListEvent.Search -> {
                search(event.query)
            }
            is ListEvent.SortBy -> {
                mutableOpt.update {
                    it.copy(orderEnum = event.sortEnum)
                }
            }
        }
    }


    private fun search(query: String){
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(700)
            mutableOpt.update {
                it.copy(query = query)
            }
        }
    }

}

fun <T>MutableLiveData<T>.update(transform: (T) -> T){
    this.value = this.value?.let { transform(it) }
}
