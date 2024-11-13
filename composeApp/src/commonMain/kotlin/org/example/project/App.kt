package org.example.project

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Text("Hello ${getPlatform().name}")
        LaunchedEffect(Unit) {
            fetchWeatherData("80550")
        }
    }
}
