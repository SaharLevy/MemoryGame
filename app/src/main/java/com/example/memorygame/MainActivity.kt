package com.example.memorygame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.memorygame.ui.DifficultySelectionScreen
import com.example.memorygame.ui.GameScreen
import com.example.memorygame.ui.HomeScreen
import com.example.memorygame.ui.theme.MemoryGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoryGameTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MemoryGameApp()
                }
            }
        }
    }
}

@Composable
fun MemoryGameApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("difficulty") { DifficultySelectionScreen(navController) }
        composable("game/{difficulty}") { backStackEntry ->
            GameScreen(navController, backStackEntry.arguments?.getString("difficulty") ?: "normal")
        }
    }
}
