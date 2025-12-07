package com.muhammad.brain.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class QuizCategory(
    val id : Int,
    val name : String,
    val icon : Int, val brushColors : List<Color>
)