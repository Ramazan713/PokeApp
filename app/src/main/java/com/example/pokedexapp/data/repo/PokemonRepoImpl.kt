package com.example.pokedexapp.data.repo

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import androidx.paging.map
import com.example.pokedexapp.data.local.AppDatabase
import com.example.pokedexapp.data.mapper.toPokemonDetail
import com.example.pokedexapp.data.mapper.toPokemonPart
import com.example.pokedexapp.data.remote.PokeRemoteMediator
import com.example.pokedexapp.data.remote.services.PokeApiServiceHelper
import com.example.pokedexapp.domain.constants.K
import com.example.pokedexapp.domain.enums.OrderEnum
import com.example.pokedexapp.domain.models.LoadOpt
import com.example.pokedexapp.domain.models.PokemonDetail
import com.example.pokedexapp.domain.models.PokemonPart
import com.example.pokedexapp.domain.repo.PokemonRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonRepoImpl @Inject constructor(
    private val apiHelper: PokeApiServiceHelper,
    private val db: AppDatabase,
    private val sharedPreferences: SharedPreferences,
): PokemonRepo {
    override fun getPokemonDetailsPaging(opt: LoadOpt): LiveData<PagingData<PokemonDetail>> {
        val pager = Pager(
            config = PagingConfig(
                pageSize = K.pageSize
            ),
            initialKey = 1,
            pagingSourceFactory = {
                when(opt.orderEnum){
                    OrderEnum.Number -> {
                        db.pokemonDao().getPokemonDetailsOrderById(opt.remoteKeyLabel)
                    }
                    OrderEnum.Name -> {
                        db.pokemonDao().getPokemonDetailsOrderByName(opt.remoteKeyLabel)
                    }
                }
            }
        )
        return pager.liveData.map {pagingData->
            pagingData.map { it.toPokemonDetail() }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getPokemonsPaging(opt: LoadOpt): Flow<PagingData<PokemonPart>> {

        val pager = Pager(
            config = PagingConfig(
                pageSize = K.pageSize,
            ),
            initialKey = 1,
            remoteMediator = PokeRemoteMediator(
                db = db,
                pageSize = K.pageSize,
                pokeApiServiceHelper = apiHelper,
                sharedPreferences = sharedPreferences,
                initExtraPageCount = 2,
                loadOpt = opt
            ),
            pagingSourceFactory = {
                when(opt.orderEnum){
                    OrderEnum.Number -> {
                        db.pokemonDao().getPokemonsOrderById(opt.remoteKeyLabel)
                    }
                    OrderEnum.Name -> {
                        db.pokemonDao().getPokemonsOrderByName(opt.remoteKeyLabel)
                    }
                }
            }
        )
        return pager.flow.map {pagingData->
            pagingData.map { it.toPokemonPart() }
        }
    }
}