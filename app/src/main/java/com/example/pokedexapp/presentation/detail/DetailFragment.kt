package com.example.pokedexapp.presentation.detail

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.view.marginRight
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pokedexapp.R
import com.example.pokedexapp.databinding.FragmentDetailBinding
import com.example.pokedexapp.databinding.FragmentListBinding
import com.example.pokedexapp.domain.extensions.fillWith
import com.example.pokedexapp.domain.utils.Colors
import com.example.pokedexapp.presentation.utils.downloadUrl
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel by viewModels<DetailViewModel>()

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("pokemonId")?.let { pokemonId->
            viewModel.loadData(pokemonId)
        }

        binding.barNavigateBack.setOnClickListener {
            findNavController().navigateUp()
        }

        observeData()
    }

    private fun observeData(){
        viewModel.pokemonData.observe(viewLifecycleOwner){data->
            if(data == null) return@observe
            val pokemon = data.pokemon
            val color = data.baseColor

            binding.barTitle.text = pokemon.name
            binding.barNumber.text = pokemon.idWithHash

            binding.root.backgroundTintList = ColorStateList.valueOf(color)
            binding.image.downloadUrl(pokemon.imageUrl)
            binding.card.baseStateText.setTextColor(color)
            binding.card.aboutText.setTextColor(color)

            binding.card.chipLayout.let { l ->
                data.types.forEach { type->
                    val chip = Chip(requireContext()).apply {
                        text = type.name
                        setTextColor(requireContext().getColor(R.color.onBrandColor))
                        chipBackgroundColor = ColorStateList.valueOf(Colors.getColor(type.name))
                        chipStrokeWidth = 0f
                    }
                    l.addView(chip,MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                        .apply { setMargins(0,0,16,0) })
                }
            }

            binding.card.physicalInfo.let { info->
                info.weightText.text = "${pokemon.weightInKg} kg"
                info.heightText.text = "${pokemon.heightInMetre} kg"
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

        }
    }
}


private fun ProgressBar.setData(color: Int, progress: Int){
    val drawable = this.progressDrawable as LayerDrawable
    drawable.findDrawableByLayerId(R.id.progressBackgroundLayer).setTint(ColorUtils.setAlphaComponent(color,50))
    drawable.findDrawableByLayerId(R.id.progressLayer).setTint(color)

    this.setProgress(progress,true)
}

