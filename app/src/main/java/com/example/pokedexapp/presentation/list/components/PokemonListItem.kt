package com.example.pokedexapp.presentation.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.pokedexapp.R
import com.example.pokedexapp.domain.models.Pokemon
import com.example.pokedexapp.domain.models.PokemonPart

@Composable
fun PokemonListItem(
    modifier: Modifier = Modifier,
    item: PokemonPart,
    onClick: () -> Unit
) {

    val backgroundShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
    val shape = MaterialTheme.shapes.medium

    Card(
        modifier = modifier
            .padding(4.dp)
            .clip(shape)
            .clickable {
                onClick()
            }
            ,
        shape = shape
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {


            Box(modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color(0xFFE0E0E0), shape = backgroundShape)
                .fillMaxWidth()
                .height(90.dp)
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .zIndex(2f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = Pokemon.getIdWithHash(item.pokemonId),
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.typography.bodyMedium.color.copy(alpha = 0.7f)
                    )
                )
                AsyncImage(
                    modifier = Modifier
                        .height(90.dp)
                        .fillMaxWidth()
                    ,
                    model = item.imageUrl,
                    contentDescription = null,
                    error = painterResource(id = R.drawable.baseline_error_24),
                    placeholder = painterResource(id = R.drawable.baseline_downloading_24)
                )
                Text(
                    text = item.name,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PokemonListItemPreview() {
    PokemonListItem(
        item = PokemonPart(
            pokemonId = 1,
            name = "Balbu",
            imageUrl = ""
        ),
        onClick = {

        }
    )
}