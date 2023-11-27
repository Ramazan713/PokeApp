package com.example.pokedexapp.presentation.list.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokedexapp.presentation.list.ListPage
import com.example.pokedexapp.presentation.list.ListViewModel

typealias Position = Int
typealias Query = String
typealias OrderEnum = Int

const val ListRouteName = "list"

fun NavGraphBuilder.listPage(
    onNavigateToDetail: (Position,Query,OrderEnum) -> Unit
){
    composable(ListRouteName){
        val listViewModel = hiltViewModel<ListViewModel>()

        val data = listViewModel.pagingData.collectAsLazyPagingItems()
        val state by listViewModel.state.collectAsStateWithLifecycle()

        ListPage(
            data = data,
            state = state,
            onItemClick = { _, i ->
                onNavigateToDetail(i,state.query,state.orderEnum.valueEnum)
            },
            onEvent = listViewModel::onEvent
        )
    }
}