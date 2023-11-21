package com.example.pokedexapp.presentation.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapp.databinding.PokemonListItemBinding
import com.example.pokedexapp.domain.models.Pokemon
import com.example.pokedexapp.presentation.utils.downloadUrl

class ListAdapter constructor(
    private val listener: Listener,
): PagingDataAdapter<Pokemon, ListAdapter.ListAdapterHolder>(diffCallback) {


    inner class ListAdapterHolder(val binding: PokemonListItemBinding) : RecyclerView.ViewHolder(binding.root)

    private var pokemons: List<Pokemon> = emptyList()


    interface Listener{
        fun onClick(item: Pokemon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapterHolder {
        val binding = PokemonListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListAdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: ListAdapterHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.binding.let { binding->
            binding.itemId.text = item.id.toString()
            binding.itemName.text = item.name
            binding.itemImage.downloadUrl(item.imageUrl)
            binding.root.setOnClickListener {
                listener.onClick(item)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<Pokemon>){
        this.pokemons = items
        notifyDataSetChanged()
    }



}

private val diffCallback = object : DiffUtil.ItemCallback<Pokemon>(){
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.id == newItem.id
    }

}