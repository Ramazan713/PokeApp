package com.example.pokedexapp.presentation.detail

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.LayerDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.graphics.ColorUtils
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapp.R
import com.example.pokedexapp.databinding.DetailPageItemBinding
import com.example.pokedexapp.databinding.PokemonListItemBinding
import com.example.pokedexapp.domain.extensions.fillWith
import com.example.pokedexapp.domain.models.PokemonDetail
import com.example.pokedexapp.domain.models.PokemonPart
import com.example.pokedexapp.domain.utils.Colors
import com.example.pokedexapp.presentation.detail.components.DetailPageItem
import com.example.pokedexapp.presentation.list.ListAdapter
import com.example.pokedexapp.presentation.utils.downloadUrl
import com.google.android.material.chip.Chip

class DetailAdapter constructor(
    private val context: Context,
    private val listener: Listener
): PagingDataAdapter<PokemonDetail, DetailAdapter.DetailAdapterHolder>(diffCallback)  {

    inner class DetailAdapterHolder(val binding: DetailPageItemBinding) : RecyclerView.ViewHolder(binding.root)


    interface Listener{
        fun onNavigateBackClick()
        fun onPrevious(item: PokemonDetail)
        fun onNext(item: PokemonDetail)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailAdapter.DetailAdapterHolder {
        val binding = DetailPageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailAdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailAdapter.DetailAdapterHolder, position: Int) {

        val data = getItem(position) ?: return
        val binding = holder.binding

        binding.composePageItem.setContent {
            DetailPageItem(
                pokemonDetail = data,
                onNavigateBack = {
                    listener.onNavigateBackClick()
                },
                onPrevious = {
                    listener.onPrevious(data)
                },
                onNext = {
                    listener.onNext(data)
                },
                previousVisible = position != 0,
                nextVisible = position != itemCount - 1
            )
        }

        binding.root.isVisible = true
    }
}

private val diffCallback = object : DiffUtil.ItemCallback<PokemonDetail>(){
    override fun areItemsTheSame(oldItem: PokemonDetail, newItem: PokemonDetail): Boolean {
        return oldItem.pokemon.pokemonId == newItem.pokemon.pokemonId
    }

    override fun areContentsTheSame(oldItem: PokemonDetail, newItem: PokemonDetail): Boolean {
        return oldItem.pokemon.pokemonId == newItem.pokemon.pokemonId
    }

}