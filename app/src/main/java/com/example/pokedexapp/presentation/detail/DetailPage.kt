package com.example.pokedexapp.presentation.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokedexapp.domain.models.PokemonDetail
import com.example.pokedexapp.domain.models.PokemonPart
import com.example.pokedexapp.presentation.detail.components.DetailPageItem
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailPage(
    modifier: Modifier = Modifier,
    data: LazyPagingItems<PokemonDetail>,
    posInit: Int,
    onNavigateBack: () -> Unit
) {

    val pagerState = rememberPagerState(pageCount = { data.itemCount }, initialPage = posInit)
    val scope = rememberCoroutineScope()

    val isLoading by remember(data) {
        derivedStateOf {
            data.loadState.refresh is LoadState.Loading
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ){
        if(isLoading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        HorizontalPager(
            state = pagerState
        ){pos->
            val item = data[pos] ?: return@HorizontalPager

            DetailPageItem(
                pokemonDetail = item,
                onNavigateBack = onNavigateBack,
                onPrevious = {
                    scope.launch {
                        pagerState.animateScrollToPage(pos - 1)
                    }
                },
                onNext = {
                    scope.launch {
                        pagerState.animateScrollToPage(pos + 1)
                    }
                },
                previousVisible = pos != 0,
                nextVisible = pagerState.pageCount - 1 != pos
            )

        }

    }
}


@Preview(showBackground = true)
@Composable
fun DetailPagePreview() {

    val data = flow<PagingData<PokemonDetail>> {  }.collectAsLazyPagingItems()


    DetailPage(
        data = data,
        posInit = 0,
        onNavigateBack = {}
    )
}