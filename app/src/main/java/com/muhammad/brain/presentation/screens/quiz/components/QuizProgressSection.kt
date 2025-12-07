package com.muhammad.brain.presentation.screens.quiz.components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.muhammad.brain.presentation.components.AppProgressBar

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun QuizProgressSection(
    modifier: Modifier = Modifier,
    activeQuestion: Int,isQuestionLocked : Boolean,
    totalQuestions: Int,
    timeLeft: Int,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val completionProgress = activeQuestion.toFloat() / totalQuestions.toFloat()
    val remainingTimeProgress = timeLeft.toFloat() / 20f
    val animatedRemainingTimeProgress by animateFloatAsState(
        targetValue = remainingTimeProgress,
        animationSpec = MaterialTheme.motionScheme.slowEffectsSpec(),
        label = "remainingTimeProgress"
    )
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.5f, targetValue = 1f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "alpha"
    )
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                text = "Question $activeQuestion/$totalQuestions",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
            )
            AppProgressBar(
                progress = completionProgress,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Box(modifier = Modifier.size(75.dp), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = { 1f },
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(if(isQuestionLocked) alpha else 1f),
                strokeWidth = 6.dp,
                strokeCap = StrokeCap.Round
            )
            CircularProgressIndicator(
                progress = { animatedRemainingTimeProgress },
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 6.dp, strokeCap = StrokeCap.Round
            )
            Text(
                text = timeLeft.toString(),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
            )
        }
    }
}