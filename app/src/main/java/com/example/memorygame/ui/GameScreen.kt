package com.example.memorygame.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.memorygame.R
import com.example.memorygame.viewmodel.GameViewModel
import com.example.memorygame.model.Card
import com.example.memorygame.viewmodel.GameViewModelFactory
import androidx.navigation.NavController

@Composable
fun GameScreen(navController: NavController, difficulty: String) {
    val gameViewModel: GameViewModel = viewModel(factory = GameViewModelFactory())
    LaunchedEffect(difficulty) {
        gameViewModel.resetGame(difficulty)
    }
    val cards by gameViewModel.cards.collectAsState()
    val attempts by gameViewModel.attempts.collectAsState()
    val gameOver by gameViewModel.gameOver.collectAsState()

    if (gameOver) {
        GameOverScreen(navController) { gameViewModel.resetGame(difficulty) }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Memory Game", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Attempts left: ${5 - attempts}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))

            val columns = if (difficulty == "normal") 4 else 6
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                //modifier = Modifier.fillMaxWidth()
            ) {
                items(cards) { card ->
                    CardItem(card = card, onClick = { gameViewModel.onCardClicked(card) })
                }
            }
        }
    }
}

@Composable
fun CardItem(card: Card, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (card.isFlipped) {
            Image(
                painter = rememberAsyncImagePainter(card.imageResId),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(R.drawable.questionmark_picture),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
