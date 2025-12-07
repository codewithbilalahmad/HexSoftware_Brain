package com.muhammad.brain.presentation.screens.quiz

sealed interface QuizEvent{
    data object OnNavigateBack : QuizEvent
}