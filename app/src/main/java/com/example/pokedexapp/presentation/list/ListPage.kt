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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokedexapp.R
import com.example.pokedexapp.domain.models.PokemonPart
import com.example.pokedexapp.presentation.filter_dialog.OrderDialog
import com.example.pokedexapp.presentation.list.components.ListTopBar
import com.example.pokedexapp.presentation.list.components.PokemonListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun ListPage(
    modifier: Modifier = Modifier,
    data: LazyPagingItems<PokemonPart>,
    onItemClick: (PokemonPart, Int) -> Unit,
    onOrderByClick: () -> Unit,
    onEvent: (ListEvent) -> Unit
) {
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
                onOrderByClick = onOrderByClick
            )
        },
        containerColor = colorResource(id = R.color.brandColor)
    ) {paddings->
        Box(modifier = Modifier.padding(paddings)){
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxSize()
                    .background(colorResource(id = R.color.onBrandColor),RoundedCornerShape(8.dp))
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
}

@Preview(showBackground = true)
@Composable
fun ListPagePreview() {

    val data = flow<PagingData<PokemonPart>> {  }.collectAsLazyPagingItems()

    ListPage(
        onItemClick = {p,i->},
        onOrderByClick = {},
        onEvent = {},
        data = data
    )
}