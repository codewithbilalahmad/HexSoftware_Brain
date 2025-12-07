package com.muhammad.brain.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizQuestionResponseDto(
    @SerialName("response_code")
    val responseCode: Int,
    @SerialName("results")
    val questions: List<QuizQuestionDto>
)
