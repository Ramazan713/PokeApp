package com.example.pokedexapp.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokedexapp.R
import com.example.pokedexapp.domain.enums.OrderEnum
import com.example.pokedexapp.domain.models.PokemonPart
import com.example.pokedexapp.presentation.filter_dialog.OrderDia
import com.example.pokedexapp.presentation.filter_dialog.OrderDialog
import com.example.pokedexapp.presentation.list.components.ListTopBar
import com.example.pokedexapp.presentation.list.components.PokemonListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun ListPage(
    modifier: Modifier = Modifier,
    data: LazyPagingItems<PokemonPart>,
    state: ListState,
    onItemClick: (PokemonPart, Int) -> Unit,
    onEvent: (ListEvent) -> Unit
) {

    var showOrderDia by rememberSaveable {
        mutableStateOf(false)
    }

    val isLoading by remember(data) {
        derivedStateOf {
            data.loadState.refresh is LoadState.Loading
        }
    }


    Scaffold(
        modifier = modifier,
        topBar = {
            ListTopBar(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 24.dp)
                    .padding(horizontal = 12.dp)
                ,
                onValueChange = { q->
                    onEvent(ListEvent.Search(q))
                },
                onOrderByClick = {
                    showOrderDia = true
                }
            )
        },
        containerColor = colorResource(id = R.color.brandColor)
    ) {paddings->
        Box(modifier = Modifier.padding(paddings)){

            if(isLoading){
                CircularProgressIndicator(
                    modifier = Modifier.zIndex(2f).align(Alignment.Center)
                )
            }


            LazyVerticalGrid(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxSize()
                    .background(colorResource(id = R.color.onBrandColor), RoundedCornerShape(8.dp))
                ,
                columns = GridCells.Adaptive(130.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(
                    data.itemCount
                ){index->
                    val item = data[index] ?: return@items

                    PokemonListItem(
                        item = item,
                        onClick = {
                            onItemClick(item,index)
                        }
                    )
                }
            }

        }
    }


    if(showOrderDia){
        OrderDia(
            dismiss = { showOrderDia = false },
            orderEnum = state.orderEnum,
            onSelected = {
                onEvent(ListEvent.SortBy(it))
            }
        )
    }

}

@Preview(showBackground = true)
@Composable
fun ListPagePreview() {

    val data = flow<PagingData<PokemonPart>> {  }.collectAsLazyPagingItems()

    ListPage(
        onItemClick = {p,i->},
        onEvent = {},
        data = data,
        state = ListState()
    )
}