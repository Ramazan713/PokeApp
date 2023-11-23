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
import androidx.core.view.doOnAttach
import androidx.core.view.isVisible
import androidx.core.view.marginRight
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.pokedexapp.R
import com.example.pokedexapp.databinding.FragmentDetailBinding
import com.example.pokedexapp.databinding.FragmentListBinding
import com.example.pokedexapp.domain.extensions.fillWith
import com.example.pokedexapp.domain.models.PokemonDetail
import com.example.pokedexapp.domain.utils.Colors
import com.example.pokedexapp.presentation.utils.downloadUrl
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class DetailFragment : Fragment(), DetailAdapter.Listener {

    private val viewModel by viewModels<DetailViewModel>()
    private lateinit var adapter: DetailAdapter

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

        adapter = DetailAdapter(requireContext(),this)

        arguments?.getInt("pokemonId")?.let { pokemonId->
            viewModel.loadPos(pokemonId)
        }

        initViews()
        observeData()
    }

    private fun initViews(){
        binding.viewPager.adapter = adapter
    }

    private fun observeData(){
        viewModel.pagingData.observe(viewLifecycleOwner){data->
            lifecycleScope.launch {
                adapter.submitData(data)
            }
        }

        viewModel.pos.observe(viewLifecycleOwner){pos->
            binding.viewPager.postDelayed({
                binding.progressBar.isVisible = false
                binding.viewPager.setCurrentItem(pos,false)
            },50)
        }
    }

    override fun onNavigateBackClick() {
        findNavController().navigateUp()
    }

    override fun onPrevious(item: PokemonDetail) {
        binding.viewPager.let { pager->
            pager.currentItem = pager.currentItem - 1
        }
    }

    override fun onNext(item: PokemonDetail) {
        binding.viewPager.let { pager->
            pager.currentItem = pager.currentItem + 1
        }
    }
}


