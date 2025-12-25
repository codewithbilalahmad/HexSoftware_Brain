package com.muhammad.brain.presentation.screens.quiz.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammad.brain.R
import com.muhammad.brain.domain.model.QuizAnswerState
import com.muhammad.brain.domain.model.QuizQuestion
import com.muhammad.brain.presentation.theme.Green
import com.muhammad.brain.presentation.theme.GreenContainer

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
fun LazyListScope.reviewAnswerOptions(
    modifier: Modifier = Modifier,
    quizQuestion: QuizQuestion
) {
    val options = quizQuestion.options
    itemsIndexed(options, key = { id, _ -> id }) { index, option ->
        val isSelected = option == quizQuestion.selectedAnswer
        val correctAnswer = quizQuestion.correctAnswer
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
        val badgeColor by animateColorAsState(
            targetValue = when {
                isSelected && quizQuestion.answerState == QuizAnswerState.Correct -> GreenContainer

                isSelected && quizQuestion.answerState == QuizAnswerState.Wrong -> MaterialTheme.colorScheme.errorContainer
                !isSelected && option == correctAnswer && quizQuestion.answerState == QuizAnswerState.Wrong -> GreenContainer

                !isSelected && option == correctAnswer && quizQuestion.answerState == QuizAnswerState.Skipped -> GreenContainer

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
        val labelRes = when {
            option == correctAnswer -> R.string.correct
            isSelected && quizQuestion.answerState == QuizAnswerState.Wrong -> R.string.your_choice
            isSelected && quizQuestion.answerState == QuizAnswerState.Correct -> R.string.correct
            isSelected && quizQuestion.answerState == QuizAnswerState.Skipped -> R.string.correct
            else -> null
        }
        val scaleY by animateFloatAsState(
            targetValue = if (isSelected) 1.1f else 1f,
            animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(),
            label = "scale"
        )
        Card(
            modifier = modifier.graphicsLayer{
                this.scaleX = scaleX
                this.scaleY = scaleY
            },
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = containerColor),
            border = BorderStroke(width = 2.dp, color = borderColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                labelRes?.let { label ->
                    Row(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(badgeColor)
                            .padding(horizontal = 8.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = stringResource(label),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }
    }
}