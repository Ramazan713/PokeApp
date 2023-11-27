package com.example.pokedexapp.presentation.detail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.pokedexapp.R
import com.example.pokedexapp.domain.models.Pokemon
import com.example.pokedexapp.domain.models.PokemonDetail
import com.example.pokedexapp.presentation.utils.SampleData

@Composable
fun DetailPageItem(
    modifier: Modifier = Modifier,
    pokemonDetail: PokemonDetail,
    onNavigateBack: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    previousVisible: Boolean,
    nextVisible: Boolean
) {
    val pokemon = pokemonDetail.pokemon
    val baseColor = Color(pokemonDetail.baseColor)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(baseColor)
            .verticalScroll(rememberScrollState())
    ){
        Image(
            painter = painterResource(id = R.drawable.pokeball),
            contentDescription = "pokeball",
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.TopEnd)
                .alpha(0.3f)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(2f)
                .padding(4.dp)
        ) {
            GetHeader(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp, bottom = 24.dp),
                pokemon = pokemon,
                onBackClick = onNavigateBack
            )

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 70.dp)
                    .zIndex(3f),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ){
                    if(previousVisible){
                        IconButton(
                            onClick = onPrevious
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "previous",
                                tint = colorResource(id = R.color.onBrandColor)
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier.weight(4f),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(250.dp),
                        model = pokemon.imageUrl,
                        contentDescription = pokemon.name,
                        placeholder = painterResource(id = R.drawable.baseline_downloading_24),
                        error = painterResource(id = R.drawable.baseline_error_24)
                    )
                }

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ){
                    if(nextVisible){
                        IconButton(
                            onClick = onNext
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                contentDescription = "next",
                                tint = colorResource(id = R.color.onBrandColor)
                            )
                        }
                    }
                }

            }

            PokemonDetailCard(
                pokemonDetail = pokemonDetail,
                baseColor = baseColor
            )

        }
    }
}

@Composable
fun GetHeader(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    onBackClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back",
                tint = colorResource(id = R.color.onBrandColor)
            )
        }

        Spacer(modifier = Modifier.padding(horizontal = 8.dp))

        Text(
            text = pokemon.name,
            style = MaterialTheme.typography.titleLarge.copy(
                color = colorResource(id = R.color.onBrandColor),
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.weight(1f)
        )

        Text(
            text = pokemon.idWithHash,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = colorResource(id = R.color.onBrandColor),
                fontWeight = FontWeight.Bold
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DetailPageItemPreview() {
    DetailPageItem(
        pokemonDetail = SampleData.pokemonDetail,
        onNavigateBack = {},
        onNext = {  },
        onPrevious = {  },
        previousVisible = false,
        nextVisible = true
    )
}