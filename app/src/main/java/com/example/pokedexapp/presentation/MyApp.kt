package com.example.pokedexapp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokedexapp.presentation.detail.DetailPage
import com.example.pokedexapp.presentation.detail.DetailViewModel
import com.example.pokedexapp.presentation.detail.navigation.detailPage
import com.example.pokedexapp.presentation.detail.navigation.navigateToDetailPage
import com.example.pokedexapp.presentation.list.ListPage
import com.example.pokedexapp.presentation.list.ListViewModel
import com.example.pokedexapp.presentation.list.navigation.ListRouteName
import com.example.pokedexapp.presentation.list.navigation.OrderEnum
import com.example.pokedexapp.presentation.list.navigation.Position
import com.example.pokedexapp.presentation.list.navigation.Query
import com.example.pokedexapp.presentation.list.navigation.listPage

@Composable
fun MyApp() {

    val navHostController = rememberNavController()

    NavHost(
        navHostController,
        startDestination = ListRouteName
    ){
        listPage(
            onNavigateToDetail = {pos: Position, query: Query, orderEnum: OrderEnum ->
                navHostController.navigateToDetailPage(pos,query,orderEnum)
            }
        )

        detailPage(
            onNavigateBack = {
                navHostController.navigateUp()
            }
        )
    }

}