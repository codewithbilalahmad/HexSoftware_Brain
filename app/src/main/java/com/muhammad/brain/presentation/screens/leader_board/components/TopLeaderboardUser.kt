package com.muhammad.brain.presentation.screens.leader_board.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.muhammad.brain.R
import com.muhammad.brain.domain.model.LeaderboardUser

@Composable
fun TopLeaderboardUser(
    modifier: Modifier = Modifier,
    leaderboardUser: LeaderboardUser,
    color: Color,
    isFirstUser: Boolean,
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if (isFirstUser) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_crown),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .offset(y = 4.dp)
            )
        }
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(leaderboardUser.image),
                contentDescription = null,
                modifier = Modifier
                    .size(if (isFirstUser) 120.dp else 90.dp)
                    .clip(CircleShape)
                    .border(width = 4.dp, color = color, shape = CircleShape)
            )
            Box(
                modifier = Modifier
                    .offset(y = 12.dp)
                    .align(Alignment.BottomCenter)
                    .size(if(isFirstUser) 30.dp else 25.dp)
                    .clip(CircleShape)
                    .background(color)
                    .padding(horizontal = 8.dp), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = leaderboardUser.rank.toString(),
                    style = if(isFirstUser) MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    ) else MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                )
            }
        }
        Spacer(Modifier.height(24.dp))
        Text(
            text = leaderboardUser.username,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
        )
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .clip(CircleShape)
                .background(color)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_coin),
                contentDescription = null, modifier = Modifier.size(20.dp)
            )
            Text(
                text = leaderboardUser.coin.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }
    }
}