package com.muhammad.brain.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class QuizCategoryResponseDto(
    val trivia_categories : List<QuizCategoryDto>
)