package com.muhammad.brain.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Destinations {
    @Serializable
    data object HomeScreen : Destinations

    @Serializable
    data class QuizScreen(
        val categoryId: Int,
        val categoryName: String,
        val difficulty: String,
        val questionCount: Int,
    ) : Destinations

    @Serializable
    data object LeaderboardScreen : Destinations
}