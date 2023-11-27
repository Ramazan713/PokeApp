package com.example.pokedexapp.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.R
import com.example.pokedexapp.domain.models.PokemonDetail
import com.example.pokedexapp.domain.utils.Colors
import com.example.pokedexapp.presentation.utils.SampleData

@Composable
fun PokemonDetailCard(
    modifier: Modifier = Modifier,
    pokemonDetail: PokemonDetail,
    colors: CardColors? = null,
    baseColor: Color
) {
    val shape = RoundedCornerShape(8.dp)

    val pokemon = pokemonDetail.pokemon

    Card(
        modifier = modifier,
        shape = shape,
        colors = colors ?: CardDefaults.cardColors()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 56.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                pokemonDetail.types.forEach { type->
                    AssistChip(
                        modifier = Modifier.padding(end = 16.dp),
                        onClick = {  }, 
                        label = { Text(text = type.name) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color(Colors.getColor(type.name)),
                            labelColor = colorResource(id = R.color.onBrandColor)
                        ),
                        border = null
                    )
                }
            }

            GetTitle(
                title = "About",
                color = baseColor
            )

            PhysicalInfo(
                weightValue = "${pokemon.weightInKg} kg",
                heightValue = "${pokemon.heightInMetre} m",
                movesValue = pokemonDetail.moves.joinToString("\n") { it.name }
            )

            Text(
                text = pokemon.flavorText,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            GetTitle(
                title = "Base Stats",
                color = baseColor
            )

            StatsDetailInfo(
                pokemon = pokemon,
                baseColor = baseColor
            )

        }
    }
}


@Composable
private fun GetTitle(
    modifier: Modifier = Modifier,
    title: String,
    color: Color
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = color
        ),
        modifier = modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}



@Preview(showBackground = true)
@Composable
fun PokemonDetailCardPreview() {
    
    val pokemonDetail = SampleData.pokemonDetail
    
    PokemonDetailCard(
        pokemonDetail = pokemonDetail,
        baseColor = Color.Green
    )
}