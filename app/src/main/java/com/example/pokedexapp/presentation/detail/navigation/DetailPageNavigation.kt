package com.example.pokedexapp.presentation.detail.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokedexapp.presentation.detail.DetailPage
import com.example.pokedexapp.presentation.detail.DetailViewModel


private const val RouteName = "detail/{pos}/{query}/{orderEnumValue}"

data class DetailArgs(
    val position: Int,
    val query: String,
    val orderEnumValue: Int
){
    constructor(savedStateHandle: SavedStateHandle): this(
        checkNotNull(savedStateHandle["pos"]) as Int,
        getSafeQueryString(checkNotNull(savedStateHandle["query"]) as String),
        checkNotNull(savedStateHandle["orderEnumValue"]) as Int,
    )
}


fun NavController.navigateToDetailPage(
    position: Int,
    query: String,
    orderEnumValue: Int
){
    this.navigate("detail/$position/${setSafeQueryString(query)}/$orderEnumValue")
}

fun NavGraphBuilder.detailPage(
    onNavigateBack: () -> Unit
){
    composable(
        RouteName,
        arguments = listOf(
            navArgument("pos"){ type = NavType.IntType },
            navArgument("query"){ type = NavType.StringType },
            navArgument("orderEnumValue"){ type = NavType.IntType },
        )
    ){

        val detailViewModel = hiltViewModel<DetailViewModel>()
        val args = detailViewModel.args

        val data = detailViewModel.pagingData.collectAsLazyPagingItems()

        DetailPage(
            data = data,
            posInit = args.position,
            onNavigateBack = onNavigateBack
        )
    }
}


private fun setSafeQueryString(query: String): String{
    if(query.isBlank()) return "*"
    return query
}

private fun getSafeQueryString(query: String): String{
    if(query == "*") return ""
    return query
}

