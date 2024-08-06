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
fun SettingsScreen(navController: NavController, settingsViewModel: SettingsViewModel = viewModel()) {
    val backgroundColor by settingsViewModel.backgroundColor.collectAsState()
    val cardFlipSoundEnabled by settingsViewModel.cardFlipSoundEnabled.collectAsState()
    val timerEnabled by settingsViewModel.timerEnabled.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Settings", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Background Color")
        Spacer(modifier = Modifier.height(8.dp))
        var expanded by remember { mutableStateOf(false) }
        Box(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { expanded = !expanded }) {
                Text(text = backgroundColor)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("White") },
                    onClick = { settingsViewModel.setBackgroundColor("White"); expanded = false }
                )
                DropdownMenuItem(
                    text = { Text("LightGray") },
                    onClick = { settingsViewModel.setBackgroundColor("LightGray"); expanded = false }
                )
                DropdownMenuItem(
                    text = { Text("Yellow") },
                    onClick = { settingsViewModel.setBackgroundColor("Yellow"); expanded = false }
                )
                DropdownMenuItem(
                    text = { Text("Green") },
                    onClick = { settingsViewModel.setBackgroundColor("Green"); expanded = false }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Card Flip Sound")
        Spacer(modifier = Modifier.height(8.dp))
        Switch(
            checked = cardFlipSoundEnabled,
            onCheckedChange = { settingsViewModel.setCardFlipSoundEnabled(it) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Timer")
        Spacer(modifier = Modifier.height(8.dp))
        Switch(
            checked = timerEnabled,
            onCheckedChange = { settingsViewModel.setTimerEnabled(it) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("home") }) {
            Text(text = "Back to Home")
        }
    }
}
