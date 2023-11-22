package com.example.pokedexapp.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.pokedexapp.domain.models.PokemonPart
import com.example.pokedexapp.domain.repo.PokemonRepo
import com.example.pokedexapp.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val pokemonRepo: PokemonRepo
): ViewModel(){

    private val mutableData = MutableLiveData<List<PokemonPart>>()
    val data: LiveData<List<PokemonPart>> get() =  mutableData

    val pagingData = pokemonRepo.getPokemonsPaging().cachedIn(viewModelScope)

    fun loadData(){

        viewModelScope.launch {
            pokemonRepo.getPokemons().let { result->
                when(result){
                    is Resource.Error -> {

                    }
                    is Resource.Success -> {
                        mutableData.value = result.value.toList()
                    }
                }
            }
        }
    }

}