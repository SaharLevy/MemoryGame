// MemoryGameTheme.kt
package com.example.memorygame.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.memorygame.viewmodel.SettingsViewModel

@Composable
fun MemoryGameTheme(
    settingsViewModel: SettingsViewModel = viewModel(),
    content: @Composable () -> Unit
) {
    val backgroundColor by settingsViewModel.backgroundColor.collectAsState()
    val colorScheme = when (backgroundColor) {
        "White" -> MaterialTheme.colorScheme.copy(background = Color.White)
        "LightGray" -> MaterialTheme.colorScheme.copy(background = Color.LightGray)
        "Yellow" -> MaterialTheme.colorScheme.copy(background = Color.Yellow)
        "Green" -> MaterialTheme.colorScheme.copy(background = Color.Green)
        else -> MaterialTheme.colorScheme.copy(background = Color.White)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
