package com.muhammad.brain.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizQuestionDto(
    val category: String,
    @SerialName("correct_answer")
    val correctAnswer: String,
    val difficulty: String,
    @SerialName("incorrect_answers")
    val incorrectAnswers: List<String>,
    val question: String,
    val type: String
)