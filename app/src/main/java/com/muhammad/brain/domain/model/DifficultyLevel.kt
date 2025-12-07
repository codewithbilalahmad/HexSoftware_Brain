package com.muhammad.brain.domain.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.muhammad.brain.R

@Immutable
enum class DifficultyLevel(@get:StringRes val label: Int, val icon : Int) {
    Easy(label = R.string.easy, icon = R.drawable.ic_easy),
    Medium(label =R.string.medium, icon = R.drawable.ic_medium),
    Hard(label =R.string.hard, icon = R.drawable.ic_hard)
}