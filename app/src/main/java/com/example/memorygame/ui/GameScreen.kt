package com.example.memorygame.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.memorygame.R
import com.example.memorygame.viewmodel.GameViewModel
import com.example.memorygame.viewmodel.SettingsViewModel
import com.example.memorygame.model.Card
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun GameScreen(navController: NavController, settingsViewModel: SettingsViewModel) {
    val gameViewModel: GameViewModel = viewModel()

    val cards by gameViewModel.cards.collectAsState()
    val attempts by gameViewModel.attempts.collectAsState()
    val gameOver by gameViewModel.gameOver.collectAsState()
    val difficulty by settingsViewModel.difficulty.collectAsState()
    val backgroundColor = settingsViewModel.backgroundColor.collectAsState().value
    val timerEnabled by settingsViewModel.timerEnabled.collectAsState()
    val cardFlipSoundEnabled by settingsViewModel.cardFlipSoundEnabled.collectAsState()
    val timer by gameViewModel.timer.collectAsState()

    LaunchedEffect(timerEnabled) {
        if (timerEnabled) {
            gameViewModel.startTimer()
        } else {
            gameViewModel.stopTimer()
        }
    }

    LaunchedEffect(difficulty) {
        gameViewModel.resetGame(difficulty)
    }

    if (gameOver) {
        GameOverScreen(navController, timer.toInt(), timerEnabled) { gameViewModel.resetGame(difficulty) }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(android.graphics.Color.parseColor(backgroundColor)))
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Memory Game", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Attempts left: ${5 - attempts}", style = MaterialTheme.typography.bodyMedium)
            if (timerEnabled) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Time: $timer seconds", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (difficulty == "normal") {
                NormalDifficultyGrid(cards, gameViewModel, cardFlipSoundEnabled)
            } else {
                HardDifficultyGrid(cards, gameViewModel, cardFlipSoundEnabled)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                gameViewModel.stopTimer()
                navController.navigate("home")
            }) {
                Text(text = "Go to Main Page")
            }
        }
    }
}

@Composable
fun NormalDifficultyGrid(cards: List<Card>, gameViewModel: GameViewModel, cardFlipSoundEnabled: Boolean) {
    Column {
        for (i in 0 until 4) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (j in 0 until 4) {
                    val index = i * 4 + j
                    if (index < cards.size) {
                        CardItem(card = cards[index]) { gameViewModel.onCardClicked(cards[index], cardFlipSoundEnabled) }
                    }
                }
            }
        }
    }
}

@Composable
fun HardDifficultyGrid(cards: List<Card>, gameViewModel: GameViewModel, cardFlipSoundEnabled: Boolean) {
    Column {
        for (i in 0 until 4) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (j in 0 until 7) {
                    val index = i * 7 + j
                    if (index < cards.size) {
                        CardItem(card = cards[index]) { gameViewModel.onCardClicked(cards[index], cardFlipSoundEnabled) }
                    }
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
