package com.example.memorygame.model

data class Card(
    val id: Int,
    val imageResId: Int,
    val animalName: String,
    var isFlipped: Boolean = false,
    var isMatched: Boolean = false
)
