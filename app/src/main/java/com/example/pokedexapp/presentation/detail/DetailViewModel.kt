package com.example.pokedexapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.domain.models.PokemonDetail
import com.example.pokedexapp.domain.repo.PokemonRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val pokemonRepo: PokemonRepo
): ViewModel(){

    private val mutablePokemon = MutableLiveData<PokemonDetail?>(null)
    val pokemonData: LiveData<PokemonDetail?> = mutablePokemon


    fun loadData(id: Int){
        viewModelScope.launch {
            mutablePokemon.value = pokemonRepo.getPokemonDetail(id)
        }
    }


}