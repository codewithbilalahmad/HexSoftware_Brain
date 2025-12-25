package com.muhammad.brain.presentation.screens.quiz.components

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.muhammad.brain.R
import com.muhammad.brain.domain.model.QuizAnswerState
import com.muhammad.brain.domain.model.QuizQuestion
import com.muhammad.brain.presentation.theme.GreenContainer

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun QuestionContent(
    modifier: Modifier = Modifier,
    showQuestionState: Boolean = false,
    question: QuizQuestion,
    blurRadius: Dp = 32.dp,
) {
    val density = LocalDensity.current
    val radiusPx = with(density) {
        blurRadius.toPx()
    }
    val blurModifier = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        Modifier.graphicsLayer {
            renderEffect = RenderEffect.createBlurEffect(radiusPx, radiusPx, Shader.TileMode.CLAMP)
                .asComposeRenderEffect()
        }
    } else {
        Modifier.blur(blurRadius)
    }
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(24.dp))
                .background(brush = Brush.horizontalGradient(question.brushColors))
                .then(blurModifier)
        )
        if (showQuestionState) {
            val icon = when (question.answerState) {
                QuizAnswerState.Correct -> R.drawable.ic_check
                QuizAnswerState.Wrong -> R.drawable.ic_cancel
                else -> R.drawable.ic_forward
            }
            val containerColor by animateColorAsState(
                targetValue = when (question.answerState) {
                    QuizAnswerState.Correct -> GreenContainer
                    QuizAnswerState.Wrong -> MaterialTheme.colorScheme.errorContainer
                    else -> MaterialTheme.colorScheme.primaryContainer
                },
                animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(),
                label = "containerColor"
            )
            Row(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-12).dp)
                    .clip(CircleShape)
                    .background(containerColor)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(icon),
                    contentDescription = null, tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = question.answerState.name,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                )
            }
        }
        Box(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(targetState = question.question, transitionSpec = {
                slideInVertically { -it } + expandVertically { -it } + fadeIn() togetherWith slideOutVertically { -it } + shrinkVertically { -it } + fadeOut()
            }) {questionLabel ->
                Text(
                    text = questionLabel,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}