package com.muhammad.brain.presentation.screens.leader_board

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LeaderboardViewModel : ViewModel(){
    private val _state = MutableStateFlow(LeaderboardState())
    val state = _state.asStateFlow()
}