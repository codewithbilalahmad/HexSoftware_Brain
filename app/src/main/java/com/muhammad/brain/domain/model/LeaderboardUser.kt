package com.muhammad.brain.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class LeaderboardUser(
    val rank : Int,
    val username : String,
    val image : Int,
    val coin : Int,
)
