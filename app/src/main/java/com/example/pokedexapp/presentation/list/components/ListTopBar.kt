package com.example.pokedexapp.presentation.list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.R

@Composable
fun ListTopBar(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onOrderByClick: () -> Unit
) {
    var query by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.pokeball),
                contentDescription = null
            )

            Text(
                text = "Pokédex",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = colorResource(id = R.color.onBrandColor)
                )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DefaultSearchBar(
                onValueChange = {
                    query = it
                    onValueChange(it)
                },
                value = query,
                modifier = Modifier
                    .weight(1f)
            )

            Image(
                modifier = Modifier
                    .size(48.dp)
                    .clickable { onOrderByClick() },
                painter = painterResource(id = R.drawable.orderbutton),
                contentDescription = "order"
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ListTopBarPreview() {
    ListTopBar(
        modifier = Modifier
            .padding(top = 12.dp, bottom = 24.dp)
            .padding(horizontal = 12.dp),
        onValueChange = {},
        onOrderByClick = {}
    )
}
