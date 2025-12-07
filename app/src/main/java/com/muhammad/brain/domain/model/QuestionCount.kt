package com.muhammad.brain.domain.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.muhammad.brain.R

@Immutable
enum class QuestionCount(@get:StringRes val label : Int, val count : Int){
    Ten(label = R.string.ten_qs, count = 10),
    Fifteen(label = R.string.fifteen_qs, count = 15),
    Twenty(label = R.string.twenty_qs, count = 20),
}