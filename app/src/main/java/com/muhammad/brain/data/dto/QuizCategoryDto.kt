package com.muhammad.brain.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class QuizCategoryDto(
    val id : Int,
    val name : String
)