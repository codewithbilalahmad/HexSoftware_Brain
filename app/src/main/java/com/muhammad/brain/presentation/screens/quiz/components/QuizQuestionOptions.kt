package com.muhammad.brain.presentation.screens.quiz.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammad.brain.R
import com.muhammad.brain.domain.model.QuizAnswerState
import com.muhammad.brain.domain.model.QuizQuestion

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
fun LazyListScope.quizQuestionOptions(
    modifier: Modifier = Modifier,
    quizQuestion: QuizQuestion,
    options: List<String>,
    isAnswerLocked: Boolean,
    onOptionSelected: (String) -> Unit,
) {
    itemsIndexed(options, key = { id, _ -> id }) { index, option ->
        val isSelected = option == quizQuestion.selectedAnswer
        val correctAnswer = quizQuestion.correctAnswer
        val resultIcon = when {
            isSelected && quizQuestion.answerState == QuizAnswerState.Correct -> R.drawable.ic_check
            isSelected && quizQuestion.answerState == QuizAnswerState.Wrong -> R.drawable.ic_cancel
            !isSelected && option == correctAnswer && quizQuestion.answerState == QuizAnswerState.Skipped -> R.drawable.ic_skip
            else -> null
        }
        val containerColor by animateColorAsState(
            targetValue = when {
                isSelected && quizQuestion.answerState == QuizAnswerState.Correct -> Color(
                    0xFF81C784
                )

                isSelected && quizQuestion.answerState == QuizAnswerState.Wrong -> MaterialTheme.colorScheme.error
                !isSelected && option == correctAnswer && quizQuestion.answerState == QuizAnswerState.Skipped -> Color(
                    0xFF81C784
                )

                else -> MaterialTheme.colorScheme.background
            },
            animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(),
            label = "containerColor"
        )
        val contentColor by animateColorAsState(
            targetValue = when {
                isSelected && quizQuestion.answerState == QuizAnswerState.Correct -> Color.White
                isSelected && quizQuestion.answerState == QuizAnswerState.Wrong -> MaterialTheme.colorScheme.onError
                !isSelected && option == correctAnswer && quizQuestion.answerState == QuizAnswerState.Skipped -> Color.White
                else -> MaterialTheme.colorScheme.onBackground
            },
            animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(),
            label = "contentColor"
        )
        val borderColor by animateColorAsState(
            targetValue = when {
                isSelected && quizQuestion.answerState == QuizAnswerState.Correct -> Color(
                    0xFF81C784
                )

                isSelected && quizQuestion.answerState == QuizAnswerState.Wrong -> MaterialTheme.colorScheme.error
                !isSelected && option == correctAnswer && quizQuestion.answerState == QuizAnswerState.Skipped -> Color(
                    0xFF81C784
                )

                else -> MaterialTheme.colorScheme.surfaceVariant
            },
            animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(),
            label = "borderColor"
        )
        Row(
            modifier = modifier
                .clip(CircleShape)
                .border(width = 2.dp, color = borderColor, shape = CircleShape)
                .background(containerColor)
                .animateContentSize(MaterialTheme.motionScheme.slowEffectsSpec())
                .then(
                    if (isAnswerLocked) Modifier
                    else Modifier.clickable { onOptionSelected(option) }
                )
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
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
                    color = contentColor,lineHeight = 15.sp,
                ),
                modifier = Modifier.weight(1f)
            )
            AnimatedVisibility(
                visible = resultIcon != null,
                enter = fadeIn(MaterialTheme.motionScheme.slowEffectsSpec()),
                exit = fadeOut(MaterialTheme.motionScheme.slowEffectsSpec())
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