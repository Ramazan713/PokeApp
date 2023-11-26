package com.example.pokedexapp.presentation.filter_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.pokedexapp.R
import com.example.pokedexapp.domain.enums.OrderEnum
import com.example.pokedexapp.presentation.filter_dialog.components.RadioItem

@Composable
fun OrderDia(
    dismiss: () -> Unit,
    onSelected: (OrderEnum) -> Unit,
    orderEnum: OrderEnum? = null,
) {

    var selectedItem by rememberSaveable {
        mutableStateOf(orderEnum ?: OrderEnum.Number)
    }

    Dialog(
        onDismissRequest = dismiss,
    ){
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium),
            color = colorResource(id = R.color.brandColor)
        ){
            Column(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .padding(bottom = 4.dp),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    text = "Sort By:",
                    style = MaterialTheme.typography.titleLarge.copy(
                        colorResource(id = R.color.onBrandColor),
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )

                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                        .background(
                            colorResource(id = R.color.onBrandColor),
                            RoundedCornerShape(8.dp)
                        )
                ) {
                    OrderEnum.values().forEach { enum->
                        RadioItem(
                            modifier = Modifier.padding(horizontal = 2.dp, vertical = 1.dp),
                            selected = selectedItem == enum,
                            onClick = {
                                onSelected(enum)
                                dismiss()
                            },
                            title = enum.title,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = colorResource(id = R.color.brandColor),
                                unselectedColor = colorResource(id = R.color.brandColor),
                            )
                        )
                    }
                }
            }
        }
    }
}





@Preview(showBackground = true)
@Composable
fun OrderDiaPreview() {
    OrderDia(
        dismiss = {},
        onSelected = {}
    )
}
