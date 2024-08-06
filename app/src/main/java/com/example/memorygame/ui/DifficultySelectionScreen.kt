package com.example.memorygame.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.memorygame.viewmodel.SettingsViewModel

@Composable
fun DifficultySelectionScreen(navController: NavController, settingsViewModel: SettingsViewModel = viewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Select Difficulty", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            settingsViewModel.setDifficulty("normal")
            navController.navigate("game")
        }) {
            Text(text = "Normal")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            settingsViewModel.setDifficulty("hard")
            navController.navigate("game")
        }) {
            Text(text = "Hard")
        }
    }
}
