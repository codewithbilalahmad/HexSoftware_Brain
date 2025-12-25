package com.muhammad.brain.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class QuizQuestion(
    val id : Int,
    val category: String,
    val correctAnswer: String,
    val difficulty: String,
    val options : List<String>,
    val incorrectAnswers: List<String>,
    val selectedAnswer: String? = null,
    val answerState: QuizAnswerState = QuizAnswerState.None,
    val brushColors: List<Color>,
    val question: String,
    val type: String,
)