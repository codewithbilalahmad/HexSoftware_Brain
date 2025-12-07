package com.muhammad.brain.presentation.screens.leader_board

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.muhammad.brain.R
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.brain.presentation.screens.leader_board.components.OtherLeaderboardUser
import com.muhammad.brain.presentation.screens.leader_board.components.Top3LeaderboardUsers
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(
    navHostController: NavHostController,
    viewModel: LeaderboardViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CenterAlignedTopAppBar(
            navigationIcon = {
                IconButton(onClick = {
                    navHostController.navigateUp()
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_back),
                        contentDescription = null
                    )
                }
            },
            title = {
                Text(text = stringResource(R.string.leaderboard))
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
        )
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = paddingValues
        ) {
            item("Top3LeaderboardUsers") {
                Top3LeaderboardUsers(
                    firstLeaderboardUser = state.firstLeaderboardUser,
                    secondLeaderboardUser = state.secondLeaderboardUser,
                    thirdLeaderboardUser = state.thirdLeaderboardUser
                )
            }
            items(state.otherLeaderboardUsers, key = { it.rank }) { user ->
                OtherLeaderboardUser(leaderboardUser = user, modifier = Modifier.fillMaxWidth()) { }
            }
        }
    }
}