package com.example.pokedexapp.presentation.list

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedexapp.R
import com.example.pokedexapp.databinding.FragmentListBinding
import com.example.pokedexapp.domain.enums.OrderEnum
import com.example.pokedexapp.domain.models.PokemonPart
import com.example.pokedexapp.presentation.filter_dialog.OrderDialog
import com.example.pokedexapp.presentation.list.components.ListTopBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment(), ListAdapter.Listener, OrderDialog.Listener {

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

        observeData()
    }


    private fun initView(){
        val layoutManager = GridLayoutManager(requireContext(),3)
        binding.listRecyclerView.adapter = adapter
        binding.listRecyclerView.layoutManager = layoutManager

        binding.composeSearch.setContent {
            ListTopBar(
                modifier = Modifier
//                    .padding(top = 12.dp, bottom = 24.dp)
//                    .padding(horizontal = 12.dp),
                        ,
                onValueChange = {q->
                    viewModel.onEvent(ListEvent.Search(q))
                },
                onOrderByClick = {
                    val dialog = OrderDialog(this,viewModel.sortBy.value)
                    dialog.show(childFragmentManager,null)
                }
            )
        }
    }

    private fun observeData(){
        viewModel.pagingData.observe(viewLifecycleOwner){pagingData->
           lifecycleScope.launch {
               adapter.submitData(pagingData)
           }
        }
        viewModel.sortBy.observe(viewLifecycleOwner){}

        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadState->
                binding.progressBar.isVisible = loadState.refresh is LoadState.Loading
            }
        }
    }

    override fun onClick(item: PokemonPart,position: Int) {
        val destination = ListFragmentDirections.actionHomeFragmentToDetailFragment3(
            position = position,
            query = viewModel.opt.value?.query ?: "",
            orderEnumValue = viewModel.opt.value?.orderEnum?.valueEnum ?: OrderEnum.Number.valueEnum
        )
        navController.navigate(destination)
    }

    override fun onSelected(orderEnum: OrderEnum) {
        viewModel.onEvent(ListEvent.SortBy(orderEnum))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}