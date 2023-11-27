package com.example.pokedexapp.presentation.detail.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.R

@Composable
fun PhysicalInfoItem(
    modifier: Modifier = Modifier,
    titleText: String,
    valueContent:  @Composable() (ColumnScope.() -> Unit),
) {
    Column(
        modifier = modifier.width(IntrinsicSize.Min),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        valueContent()
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = titleText,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.typography.bodyMedium.color.copy(alpha = 0.5f)
            )
        )
    }
}

@Composable
fun PhysicalInfoItem(
    modifier: Modifier = Modifier,
    @DrawableRes resId: Int,
    valueText: String,
    titleText: String
){
    PhysicalInfoItem(
        modifier = modifier,
        titleText = titleText,
    ){
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
            ,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = resId),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = valueText,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun PhysicalInfoItem(
    modifier: Modifier = Modifier,
    valueText: String,
    titleText: String
){
    PhysicalInfoItem(
        modifier = modifier,
        titleText = titleText
    ){
        Text(
            text = valueText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )
    }
}




@Preview(showBackground = true)
@Composable
fun PhysicalInfoItemPreview() {
    PhysicalInfoItem(
        resId = R.drawable.weight,
        valueText = "9.6 Kg",
        titleText = "Weight"
    )
}

@Preview(showBackground = true)
@Composable
fun PhysicalInfoItemPreview2() {
    PhysicalInfoItem(
        valueText = "9.6 Kg",
        titleText = "Weight"
    )
}