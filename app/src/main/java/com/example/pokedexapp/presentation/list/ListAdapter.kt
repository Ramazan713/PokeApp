package com.example.pokedexapp.presentation.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapp.databinding.PokemonListItemBinding
import com.example.pokedexapp.domain.models.Pokemon
import com.example.pokedexapp.domain.models.PokemonPart
import com.example.pokedexapp.presentation.utils.downloadUrl

class ListAdapter constructor(
    private val listener: Listener,
): PagingDataAdapter<PokemonPart, ListAdapter.ListAdapterHolder>(diffCallback) {


    inner class ListAdapterHolder(val binding: PokemonListItemBinding) : RecyclerView.ViewHolder(binding.root)


    interface Listener{
        fun onClick(item: PokemonPart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapterHolder {
        val binding = PokemonListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListAdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: ListAdapterHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.binding.let { binding->
            binding.itemId.text = Pokemon.getIdWithHash(item.id)
            binding.itemName.text = item.name
            binding.itemImage.downloadUrl(item.imageUrl)
            binding.root.setOnClickListener {
                listener.onClick(item)
            }
        }
    }

}

private val diffCallback = object : DiffUtil.ItemCallback<PokemonPart>(){
    override fun areItemsTheSame(oldItem: PokemonPart, newItem: PokemonPart): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PokemonPart, newItem: PokemonPart): Boolean {
        return oldItem.id == newItem.id
    }

}