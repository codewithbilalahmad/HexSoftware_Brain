package com.muhammad.brain.presentation.screens.leader_board

import com.muhammad.brain.domain.model.LeaderboardUser
import com.muhammad.brain.utils.generateRandomLeaderboardUsers

data class LeaderboardState(
    private val leaderboardUsers: List<LeaderboardUser> = generateRandomLeaderboardUsers(),
    val firstLeaderboardUser: LeaderboardUser = leaderboardUsers.first(),
    val secondLeaderboardUser: LeaderboardUser = leaderboardUsers.drop(1).first(),
    val thirdLeaderboardUser: LeaderboardUser = leaderboardUsers.drop(2).first(),
    val otherLeaderboardUsers : List<LeaderboardUser> = leaderboardUsers.drop(3)
)