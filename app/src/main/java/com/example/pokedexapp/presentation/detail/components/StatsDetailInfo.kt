package com.example.pokedexapp.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.domain.extensions.fillWith
import com.example.pokedexapp.domain.models.Pokemon
import com.example.pokedexapp.presentation.utils.SampleData

@Composable
fun StatsDetailInfo(
    pokemon: Pokemon,
    baseColor: Color
) {

    val titles = listOf("HP","ATK","DEF","SATK","SDEF","SPD")
    val titleValues = listOf(
        pokemon.hp,
        pokemon.attack,
        pokemon.defence,
        pokemon.specialAttack,
        pokemon.specialDefense,
        pokemon.speed
    )

    val maxProgressBar = 250

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
        ,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight()
        ) {
            titles.forEach { title->
                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = baseColor
                        )
                    )
                }
            }
        }

        VerticalDivider()

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            titleValues.forEach { value->
                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = value.fillWith()
                    )
                }
            }
        }

        VerticalDivider()

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            titleValues.forEach { value->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    ProgressItem(
                        color = baseColor,
                        modifier = Modifier.fillMaxWidth().height(8.dp),
                        progress = { value },
                        maxValue = { maxProgressBar }
                    )
                }
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun StatsDetailInfoPreview() {
    val pokemon = SampleData.pokemon
    StatsDetailInfo(
        pokemon,
        baseColor = Color.Red
    )
}