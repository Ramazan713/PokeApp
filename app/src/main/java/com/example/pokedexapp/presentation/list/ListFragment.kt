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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
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
class ListFragment : Fragment(), OrderDialog.Listener {

    private val viewModel by viewModels<ListViewModel>()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                val data = viewModel.pagingData.collectAsLazyPagingItems()
                val sortByState by viewModel.sortBy.collectAsStateWithLifecycle(null)

                ListPage(
                    data = data,
                    onItemClick = { _, i->
                        val destination = ListFragmentDirections.actionHomeFragmentToDetailFragment3(
                            position = i,
                            query = viewModel.opt.value.query,
                            orderEnumValue = viewModel.opt.value.orderEnum.valueEnum
                        )
                        navController.navigate(destination)
                    },
                    onOrderByClick = {
                        val dialog = OrderDialog(this@ListFragment,sortByState)
                        dialog.show(childFragmentManager,null)
                    },
                    onEvent = viewModel::onEvent)
            }
        }
    }



    override fun onSelected(orderEnum: OrderEnum) {
        viewModel.onEvent(ListEvent.SortBy(orderEnum))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}