package com.muhammad.brain.presentation.screens.leader_board.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.muhammad.brain.domain.model.LeaderboardUser

@Composable
fun Top3LeaderboardUsers(
    modifier: Modifier = Modifier,
    firstLeaderboardUser: LeaderboardUser,
    secondLeaderboardUser: LeaderboardUser,
    thirdLeaderboardUser: LeaderboardUser,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TopLeaderboardUser(
            leaderboardUser = thirdLeaderboardUser,
            color = Color(0xFFE57373), modifier = Modifier.weight(1f),
            isFirstUser = false
        )
        TopLeaderboardUser(
            leaderboardUser = firstLeaderboardUser,
            color = Color(0xFFC6B528), modifier = Modifier.weight(1.5f),
            isFirstUser = true
        )
        TopLeaderboardUser(
            leaderboardUser = secondLeaderboardUser,
            color = Color(0xFF4DD0E1), modifier = Modifier.weight(1f),
            isFirstUser = false
        )
    }
}