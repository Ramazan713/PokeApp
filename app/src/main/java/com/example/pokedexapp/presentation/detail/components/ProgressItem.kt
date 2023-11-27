package com.example.pokedexapp.presentation.detail.components

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun ProgressItem(
    modifier: Modifier = Modifier,
    progress: () -> Int,
    maxValue: () -> Int,
    color: Color = ProgressIndicatorDefaults.linearColor,
) {
    LinearProgressIndicator(
        modifier = modifier,
        progress = { progress() / maxValue().toFloat() },
        color = color,
        trackColor = color.copy(alpha = 0.5f),
        strokeCap = StrokeCap.Round
    )
}

@Composable
fun ProgressItem(
    modifier: Modifier = Modifier,
    progress: () -> Float,
    color: Color = ProgressIndicatorDefaults.linearColor,
) {
    LinearProgressIndicator(
        modifier = modifier,
        progress = progress,
        color = color,
        trackColor = color.copy(alpha = 0.5f),
        strokeCap = StrokeCap.Round
    )
}


@Preview(showBackground = true)
@Composable
fun ProgressItemPreview() {
    ProgressItem(
        progress = { 100 },
        maxValue = { 250}
    )
}


