package com.example.pokedexapp.presentation.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapp.databinding.PokemonListItemBinding
import com.example.pokedexapp.domain.models.Pokemon
import com.example.pokedexapp.presentation.utils.downloadUrl

class ListAdapter constructor(
    private val listener: Listener
): RecyclerView.Adapter<ListAdapter.ListAdapterHolder>() {


    inner class ListAdapterHolder(val binding: PokemonListItemBinding) : RecyclerView.ViewHolder(binding.root)

    private var pokemons: List<Pokemon> = emptyList()


    interface Listener{
        fun onClick(item: Pokemon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapterHolder {
        val binding = PokemonListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListAdapterHolder(binding)
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    override fun onBindViewHolder(holder: ListAdapterHolder, position: Int) {
        val item = pokemons[position]
        holder.binding.let { binding->
            binding.itemId.text = "1"
            binding.itemName.text = item.name
            binding.itemImage.downloadUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png")
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