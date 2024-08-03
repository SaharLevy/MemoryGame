package com.example.memorygame.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorygame.R
import com.example.memorygame.model.Card
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    private val _cards = MutableStateFlow<List<Card>>(emptyList())
    val cards: StateFlow<List<Card>> get() = _cards

    private val _attempts = MutableStateFlow(0)
    val attempts: StateFlow<Int> get() = _attempts

    private val _gameOver = MutableStateFlow(false)
    val gameOver: StateFlow<Boolean> get() = _gameOver

    private var firstSelectedCard: Card? = null
    private val maxAttempts = 5

    fun onCardClicked(card: Card) {
        Log.d("GameViewModel", "Card clicked: ${card.animalName}")
        val currentCards = _cards.value.toMutableList()
        Log.d("GameViewModel", "Current cards: $currentCards")
        val index = currentCards.indexOf(card)
        Log.d("GameViewModel", "Card index: $index")
        if (index == -1) return // Ensure the card is valid

        if (card.isFlipped) {
            Log.d("GameViewModel", "Card already flipped")
            return
        }

        if (firstSelectedCard == null) {
            // First card selected
            Log.d("GameViewModel", "First card selected")
            firstSelectedCard = card.copy(isFlipped = true)  // Copy and flip immediately
            currentCards[index] = firstSelectedCard!!
            _cards.value = currentCards  // Update the state to reflect the change
            Log.d("GameViewModel", "First selected card: $firstSelectedCard")
        } else {
            // Second card selected
            Log.d("GameViewModel", "Second card selected")
            val firstIndex = currentCards.indexOfFirst { it.id == firstSelectedCard!!.id }
            Log.d("GameViewModel", "First selected card index: $firstIndex")
            if (firstIndex == -1) return // Ensure the first selected card is valid

            val firstCard = firstSelectedCard // Copy the first selected card to a local variable
            currentCards[index] = card.copy(isFlipped = true)  // Flip the second card
            _cards.value = currentCards  // Update the state to reflect the second card flip

            viewModelScope.launch {
                delay(1500) // Delay for 1.5 seconds
                if (firstCard!!.imageResId == card.imageResId) {
                    // Cards match
                    Log.d("GameViewModel", "Cards match")
                    currentCards[index] = card.copy(isFlipped = true, isMatched = true)
                    currentCards[firstIndex] = firstCard.copy(isFlipped = true, isMatched = true)
                } else {
                    // Cards don't match
                    Log.d("GameViewModel", "Cards don't match")
                    currentCards[firstIndex] = firstCard.copy(isFlipped = false)
                    currentCards[index] = card.copy(isFlipped = false)
                    _attempts.value += 1
                    Log.d("GameViewModel", "Attempts: ${_attempts.value}")
                    if (_attempts.value >= maxAttempts) {
                        Log.d("GameViewModel", "Game over")
                        _gameOver.value = true
                    }
                }
                _cards.value = currentCards // Update the state to reflect the changes
            }

            firstSelectedCard = null
        }
    }

    fun resetGame(difficulty: String) {
        _cards.value = generateCards(difficulty)
        _attempts.value = 0
        _gameOver.value = false
        firstSelectedCard = null
    }

    private fun generateCards(difficulty: String): List<Card> {
        val images = listOf(
            R.drawable.bluebird_picture, R.drawable.camel_picture, R.drawable.cat_picture,
            R.drawable.dog_picture, R.drawable.dolphin_picture, R.drawable.elephant_picture,
            R.drawable.fish_picture, R.drawable.giraffe_picture, R.drawable.kangaroo_picture,
            R.drawable.lion_picture, R.drawable.monkey_picture, R.drawable.penguin_picture,
            R.drawable.rat_picture, R.drawable.whale_picture
        )
        val animalNames = listOf(
            "Blue Bird", "Camel", "Cat",
            "Dog", "Dolphin", "Elephant",
            "Fish", "Giraffe", "Kangaroo",
            "Lion", "Monkey", "Penguin",
            "Rat", "Whale"
        )

        val numberOfPairs = if (difficulty == "normal") 8 else 14
        val cards = images.take(numberOfPairs).zip(animalNames.take(numberOfPairs)).flatMap { (image, name) ->
            listOf(
                Card(id = image, imageResId = image, animalName = name),
                Card(id = image + 100, imageResId = image, animalName = name) // Ensure unique IDs
            )
        }.shuffled()

        return cards
    }
}
