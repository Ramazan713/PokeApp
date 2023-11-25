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

        val pokemon = data.pokemon
        val color = data.baseColor

        binding.barTitle.text = pokemon.name
        binding.barNumber.text = pokemon.idWithHash
        binding.nextArrow.isVisible = position != itemCount - 1
        binding.previousArrow.isVisible = position != 0

        binding.barNavigateBack.setOnClickListener {
            listener.onNavigateBackClick()
        }
        binding.nextArrow.setOnClickListener {
            listener.onNext(data)
        }
        binding.previousArrow.setOnClickListener {
            listener.onPrevious(data)
        }


        binding.root.backgroundTintList = ColorStateList.valueOf(color)
        binding.image.downloadUrl(pokemon.imageUrl)
        binding.card.baseStateText.setTextColor(color)
        binding.card.aboutText.setTextColor(color)

        binding.card.chipLayout.let { l ->
            l.removeAllViews()
            data.types.forEach { type->
                val chip = Chip(context).apply {
                    text = type.name
                    setTextColor(context.getColor(R.color.onBrandColor))
                    chipBackgroundColor = ColorStateList.valueOf(Colors.getColor(type.name))
                    chipStrokeWidth = 0f
                }
                l.addView(chip,
                    ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    .apply { setMargins(0,0,16,0) })
            }
        }

        binding.card.physicalInfo.let { info->
            info.weightText.text = "${pokemon.weightInKg} kg"
            info.heightText.text = "${pokemon.heightInMetre} m"
            info.moveText.text = data.moves.joinToString("\n") { it.name }
        }

        binding.card.statsDetail.let { l ->
            l.hpText.text = pokemon.hp.fillWith()
            l.atkText.text = pokemon.attack.fillWith()
            l.defText.text = pokemon.defence.fillWith()
            l.satkText.text = pokemon.specialAttack.fillWith()
            l.sdefText.text = pokemon.specialDefense.fillWith()
            l.spdText.text = pokemon.speed.fillWith()

            l.hpTitle.setTextColor(color)
            l.atkTitle.setTextColor(color)
            l.defTitle.setTextColor(color)
            l.satkTitle.setTextColor(color)
            l.sdefTitle.setTextColor(color)
            l.spdTitle.setTextColor(color)

            l.hpProgressBar.progressBar.setData(color,pokemon.hp)
            l.atkProgressBar.progressBar.setData(color,pokemon.attack)
            l.defProgressBar.progressBar.setData(color,pokemon.defence)
            l.satkProgressBar.progressBar.setData(color,pokemon.specialAttack)
            l.sdefProgressBar.progressBar.setData(color,pokemon.specialDefense)
            l.spdProgressBar.progressBar.setData(color,pokemon.speed)
        }

        binding.root.isVisible = true
    }
}

private fun ProgressBar.setData(color: Int, progress: Int){
    val drawable = this.progressDrawable as LayerDrawable
    drawable.findDrawableByLayerId(R.id.progressBackgroundLayer).setTint(ColorUtils.setAlphaComponent(color,50))
    drawable.findDrawableByLayerId(R.id.progressLayer).setTint(color)

    this.setProgress(progress,true)
}


private val diffCallback = object : DiffUtil.ItemCallback<PokemonDetail>(){
    override fun areItemsTheSame(oldItem: PokemonDetail, newItem: PokemonDetail): Boolean {
        return oldItem.pokemon.pokemonId == newItem.pokemon.pokemonId
    }

    override fun areContentsTheSame(oldItem: PokemonDetail, newItem: PokemonDetail): Boolean {
        return oldItem.pokemon.pokemonId == newItem.pokemon.pokemonId
    }

}