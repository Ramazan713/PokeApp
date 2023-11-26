package com.example.pokedexapp.domain.use_cases

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.pokedexapp.domain.models.LoadOpt
import com.example.pokedexapp.domain.models.PokemonPart
import com.example.pokedexapp.domain.repo.PokemonRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonsPartUseCase @Inject constructor(
    private val pokemonRepo: PokemonRepo
) {

    operator fun invoke(opt: LoadOpt): Flow<PagingData<PokemonPart>>{
        return pokemonRepo.getPokemonsPaging(opt)
    }
}