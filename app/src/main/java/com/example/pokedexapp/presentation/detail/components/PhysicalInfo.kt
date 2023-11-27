package com.example.pokedexapp.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokedexapp.R

@Composable
fun PhysicalInfo(
    modifier: Modifier = Modifier,
    weightValue: String,
    heightValue: String,
    movesValue: String
) {

    Row(
        modifier = modifier.fillMaxWidth().height(IntrinsicSize.Min),
    ) {
        PhysicalInfoItem(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            resId = R.drawable.weight,
            valueText = weightValue,
            titleText = "Weight"
        )

        VerticalDivider()

        PhysicalInfoItem(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            resId = R.drawable.straighten,
            valueText = heightValue,
            titleText = "Height"
        )

        VerticalDivider()

        PhysicalInfoItem(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            valueText = movesValue,
            titleText = "Moves"
        )
    }

}


@Preview(showBackground = true)
@Composable
fun PhysicalInfoPreview() {
    PhysicalInfo(
        heightValue = "1 w",
        weightValue = "1 kg",
        movesValue = "Chlorophyll\nOvergrow"
    )
}