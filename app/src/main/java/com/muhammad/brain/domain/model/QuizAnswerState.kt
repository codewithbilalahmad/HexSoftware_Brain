package com.muhammad.brain.domain.model

import androidx.compose.runtime.Immutable

@Immutable
enum class QuizAnswerState {
    None, Correct, Wrong, Skipped
}