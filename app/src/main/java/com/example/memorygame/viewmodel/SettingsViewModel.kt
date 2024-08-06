package com.example.memorygame.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel : ViewModel() {
    private val _backgroundColor = MutableStateFlow("White")
    val backgroundColor: StateFlow<String> get() = _backgroundColor

    private val _cardFlipSoundEnabled = MutableStateFlow(false)
    val cardFlipSoundEnabled: StateFlow<Boolean> get() = _cardFlipSoundEnabled

    private val _timerEnabled = MutableStateFlow(false)
    val timerEnabled: StateFlow<Boolean> get() = _timerEnabled

    private val _difficulty = MutableStateFlow("normal")
    val difficulty: StateFlow<String> get() = _difficulty

    fun setBackgroundColor(color: String) {
        _backgroundColor.value = color
    }

    fun setCardFlipSoundEnabled(enabled: Boolean) {
        _cardFlipSoundEnabled.value = enabled
    }

    fun setTimerEnabled(enabled: Boolean) {
        _timerEnabled.value = enabled
    }

    fun setDifficulty(difficulty: String) {
        _difficulty.value = difficulty
    }
}
