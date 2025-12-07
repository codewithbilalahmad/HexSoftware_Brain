package com.muhammad.brain.presentation.screens.leader_board.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import com.muhammad.brain.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.muhammad.brain.domain.model.LeaderboardUser

@Composable
fun OtherLeaderboardUser(
    modifier: Modifier = Modifier,
    leaderboardUser: LeaderboardUser,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier, onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(width = 2.dp, MaterialTheme.colorScheme.surfaceContainer),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = leaderboardUser.rank.toString(),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Image(
                    painter = painterResource(leaderboardUser.image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = leaderboardUser.username,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_coin),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = leaderboardUser.coin.toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        }
    }
}