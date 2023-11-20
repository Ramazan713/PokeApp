package com.example.pokedexapp.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedexapp.databinding.FragmentListBinding
import com.example.pokedexapp.domain.models.Pokemon
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(), ListAdapter.Listener {

    private val viewModel by viewModels<ListViewModel>()
    private val adapter = ListAdapter(this)

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        initView()

        viewModel.loadData()
        observeData()
    }


    private fun initView(){
        val layoutManager = GridLayoutManager(requireContext(),3)
        binding.listRecyclerView.adapter = adapter
        binding.listRecyclerView.layoutManager = layoutManager
    }

    private fun observeData(){
        viewModel.data.observe(viewLifecycleOwner){data->
            adapter.updateItems(data)
        }
    }

    override fun onClick(item: Pokemon) {
        val destination = ListFragmentDirections.actionHomeFragmentToDetailFragment3(1)
        navController.navigate(destination)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}