package com.example.memorygame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.memorygame.ui.*
import com.example.memorygame.ui.theme.MemoryGameTheme
import com.example.memorygame.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoryGameTheme {
                MemoryGameApp()
            }
        }
    }
}

@Composable
fun MemoryGameApp() {
    val navController = rememberNavController()
    val settingsViewModel: SettingsViewModel = viewModel()
    val backgroundColor = settingsViewModel.backgroundColor.collectAsState().value

    Box(modifier = Modifier.background(Color(android.graphics.Color.parseColor(backgroundColor)))) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") { HomeScreen(navController) }
            composable("game") { GameScreen(navController, settingsViewModel) }
            composable("settings") { SettingsScreen(navController, settingsViewModel) }
            composable("difficulty_selection") { DifficultySelectionScreen(navController, settingsViewModel) }
        }
    }
}
