package com.muhammad.brain.presentation.screens.quiz.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammad.brain.R
import com.muhammad.brain.domain.model.QuizAnswerState
import com.muhammad.brain.domain.model.QuizQuestion
import com.muhammad.brain.domain.model.shake.ShakeStrength
import com.muhammad.brain.domain.model.shake.rememberShakingState
import com.muhammad.brain.domain.model.shake.shakable
import com.muhammad.brain.presentation.theme.Green
import kotlin.math.sin

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
fun LazyListScope.quizQuestionOptions(
    modifier: Modifier = Modifier,
    quizQuestion: QuizQuestion,
    options: List<String>,
    isAnswerLocked: Boolean,
    onOptionSelected: (String) -> Unit,
) {
    itemsIndexed(options, key = { id, _ -> id }) { index, option ->
        val shakeState = rememberShakingState(strength = ShakeStrength.Strong)
        val isSelected = option == quizQuestion.selectedAnswer
        val scaleX by animateFloatAsState(
            targetValue = if (isSelected) 1.02f else 1f,
            animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(),
            label = "scale"
        )
        val scaleY by animateFloatAsState(
            targetValue = if (isSelected) 1.1f else 1f,
            animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(),
            label = "scale"
        )
        val elevation by animateDpAsState(
            targetValue = if (isSelected) 8.dp else 0.dp,
            animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(),
            label = "elevation"
        )
        val correctAnswer = quizQuestion.correctAnswer
        val resultIcon = when {
            isSelected && quizQuestion.answerState == QuizAnswerState.Correct -> R.drawable.ic_check
            isSelected && quizQuestion.answerState == QuizAnswerState.Wrong -> R.drawable.ic_cancel
            !isSelected && option == correctAnswer && quizQuestion.answerState == QuizAnswerState.Wrong -> R.drawable.ic_check
            !isSelected && option == correctAnswer && quizQuestion.answerState == QuizAnswerState.Skipped -> R.drawable.ic_forward
            else -> null
        }
        val containerColor by animateColorAsState(
            targetValue = when {
                isSelected && quizQuestion.answerState == QuizAnswerState.Correct -> Green

                isSelected && quizQuestion.answerState == QuizAnswerState.Wrong -> MaterialTheme.colorScheme.error
                !isSelected && option == correctAnswer && quizQuestion.answerState == QuizAnswerState.Wrong -> Green
                !isSelected && option == correctAnswer && quizQuestion.answerState == QuizAnswerState.Skipped -> Green

                else -> MaterialTheme.colorScheme.background
            },
            animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(),
            label = "containerColor"
        )
        val contentColor by animateColorAsState(
            targetValue = when {
                isSelected && quizQuestion.answerState == QuizAnswerState.Correct -> Color.White
                isSelected && quizQuestion.answerState == QuizAnswerState.Wrong -> MaterialTheme.colorScheme.onError
                !isSelected && option == correctAnswer && quizQuestion.answerState == QuizAnswerState.Wrong -> Color.White
                !isSelected && option == correctAnswer && quizQuestion.answerState == QuizAnswerState.Skipped -> Color.White
                else -> MaterialTheme.colorScheme.onBackground
            },
            animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(),
            label = "contentColor"
        )
        val borderColor by animateColorAsState(
            targetValue = when {
                isSelected && quizQuestion.answerState == QuizAnswerState.Correct -> Green

                isSelected && quizQuestion.answerState == QuizAnswerState.Wrong -> MaterialTheme.colorScheme.error
                !isSelected && option == correctAnswer && quizQuestion.answerState == QuizAnswerState.Wrong -> Green

                !isSelected && option == correctAnswer && quizQuestion.answerState == QuizAnswerState.Skipped -> Green

                else -> MaterialTheme.colorScheme.surfaceVariant
            },
            animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(),
            label = "borderColor"
        )
        LaunchedEffect(isSelected, quizQuestion.answerState) {
            if (isSelected && quizQuestion.answerState == QuizAnswerState.Wrong) {
                shakeState.snake()
            }
        }
        Card(
            modifier = modifier.shakable(state = shakeState).graphicsLayer {
                this.scaleX = scaleX
                this.scaleY = scaleY
            }, elevation = CardDefaults.cardElevation(elevation),
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = containerColor),
            border = BorderStroke(width = 2.dp, color = borderColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (isAnswerLocked) Modifier
                        else Modifier.clickable { onOptionSelected(option) }
                    )
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "${index + 1}.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = contentColor
                        )
                    )
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            color = contentColor, lineHeight = 15.sp,
                        )
                    )
                }
                AnimatedVisibility(
                    visible = resultIcon != null,
                    enter = fadeIn(MaterialTheme.motionScheme.fastEffectsSpec()),
                    exit = fadeOut(MaterialTheme.motionScheme.fastEffectsSpec())
                ) {
                    resultIcon?.let { icon ->
                        Icon(
                            imageVector = ImageVector.vectorResource(icon),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = contentColor
                        )
                    }
                }
            }
        }
    }
}