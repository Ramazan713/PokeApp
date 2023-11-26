package com.example.pokedexapp.presentation.filter_dialog.components

import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RadioItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    title: String,
    colors: RadioButtonColors? = null
) {
    val shape = MaterialTheme.shapes.medium
    ListItem(
        modifier = modifier
            .clip(shape)
            .toggleable(
                value = selected,
                role = Role.RadioButton,
                onValueChange = {
                    onClick()
                }
            ),
        headlineContent = {
            Text(text = title)
        },
        leadingContent = {
            RadioButton(
                selected = selected,
                onClick = null,
                colors = colors ?: RadioButtonDefaults.colors()
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun RadioItemPreview() {
    RadioItem(
        selected = false,
        onClick = {},
        title = "Test title"
    )
}