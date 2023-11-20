package com.example.pokedexapp.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.domain.models.Pokemon
import com.example.pokedexapp.domain.repo.PokemonRepo
import com.example.pokedexapp.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val pokemonRepo: PokemonRepo
): ViewModel(){

    private val mutableData = MutableLiveData<List<Pokemon>>()
    val data: LiveData<List<Pokemon>> get() =  mutableData


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