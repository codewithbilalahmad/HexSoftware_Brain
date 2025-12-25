package com.muhammad.brain.presentation.screens.quiz

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.brain.R
import com.muhammad.brain.domain.model.DifficultyLevel
import com.muhammad.brain.domain.utils.ObserveAsEvents
import com.muhammad.brain.presentation.components.AppAlertDialog
import com.muhammad.brain.presentation.components.ChipItem
import com.muhammad.brain.presentation.components.PrimaryButton
import com.muhammad.brain.presentation.components.SecondaryButton
import com.muhammad.brain.presentation.screens.quiz.components.QuestionContent
import com.muhammad.brain.presentation.screens.quiz.components.QuizProgressSection
import com.muhammad.brain.presentation.screens.quiz.components.QuizStatisticsCard
import com.muhammad.brain.presentation.screens.quiz.components.quizQuestionOptions
import com.muhammad.brain.presentation.screens.quiz.components.reviewAnswerOptions
import com.muhammad.brain.presentation.theme.Green
import com.muhammad.brain.utils.formatTime
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun QuizScreen(
    navHostController: NavHostController,
    viewModel: QuizViewModel = koinViewModel(),
    categoryName: String, difficultyLevel: String,
) {
    val layoutDirection = LocalLayoutDirection.current
    val transition = rememberInfiniteTransition()
    val rotation by transition.animateFloat(
        initialValue = 0f, targetValue = 360f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = FastOutLinearInEasing)
        ), label = "rotation"
    )
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            QuizEvent.OnNavigateBack -> {
                navHostController.navigateUp()
            }
        }
    }
    BackHandler {
        viewModel.onAction(QuizAction.ToggleExitQuizDialog)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(onClick = {
                            viewModel.onAction(QuizAction.ToggleExitQuizDialog)
                        }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_cancel),
                                contentDescription = null
                            )
                        }
                    },
                    title = {
                        Text(text = categoryName)
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
                )
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.5.dp,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }) { paddingValues ->
            when {
                state.isQuizQuestionLoading -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(horizontal = 32.dp, vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            24.dp,
                            Alignment.CenterVertically
                        )
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_processing),
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .graphicsLayer {
                                    rotationZ = rotation
                                }
                        )
                        Text(
                            text = stringResource(R.string.wait_to_load_quiz),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.surface
                            )
                        )
                    }
                }

                state.quizQuestionError != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(horizontal = 32.dp, vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            24.dp,
                            Alignment.CenterVertically
                        )
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_failed),
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                        )
                        Text(
                            text = state.quizQuestionError!!,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.error
                            )
                        )
                    }
                }

                state.quizQuestions.isNotEmpty() -> {
                    AnimatedContent(targetState = state.showQuizResult, transitionSpec = {
                        fadeIn() togetherWith fadeOut()
                    }, modifier = Modifier.fillMaxSize()) { showQuizResult ->
                        if (showQuizResult) {
                            val quizResultProgress =
                                (state.correctQuestions.size.toFloat() / state.quizQuestions.size.toFloat())
                            val quizResultPercentage = (quizResultProgress * 100).toInt()
                            val animatedQuizResultProgress by animateFloatAsState(
                                targetValue = quizResultProgress,
                                animationSpec = MaterialTheme.motionScheme.slowEffectsSpec(),
                                label = "quizResultProgress"
                            )
                            val animatedQuizResultPercentage by animateIntAsState(
                                targetValue = quizResultPercentage,
                                animationSpec = MaterialTheme.motionScheme.slowEffectsSpec(),
                                label = "quizResultPercentage"
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues)
                                    .verticalScroll(rememberScrollState())
                                    .padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        bottom = 16.dp,
                                        top = 32.dp
                                    ),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_trophy),
                                    contentDescription = null,
                                    modifier = Modifier.size(80.dp)
                                )
                                Spacer(Modifier.height(24.dp))
                                Text(
                                    text = stringResource(R.string.quiz_finished),
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
                                )
                                Spacer(Modifier.height(16.dp))
                                Box(
                                    modifier = Modifier.size(150.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.fillMaxSize(),
                                        progress = { 1f },
                                        color = MaterialTheme.colorScheme.surfaceVariant,
                                        strokeWidth = 12.dp,
                                        strokeCap = StrokeCap.Round
                                    )
                                    CircularProgressIndicator(
                                        modifier = Modifier.fillMaxSize(),
                                        progress = { animatedQuizResultProgress },
                                        color = MaterialTheme.colorScheme.primary,
                                        strokeWidth = 12.dp,
                                        strokeCap = StrokeCap.Round
                                    )
                                    Text(
                                        text = "$animatedQuizResultPercentage%",
                                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
                                    )
                                }
                                Spacer(Modifier.height(24.dp))
                                FlowRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(
                                        8.dp,
                                        Alignment.CenterHorizontally
                                    ),
                                    maxItemsInEachRow = 3,
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    QuizStatisticsCard(
                                        label = R.string.correct,
                                        icon = R.drawable.ic_check,
                                        modifier = Modifier.weight(1f),
                                        contentColor = Color(0xFF81C784),
                                        value = state.correctQuestions.size.toString()
                                    )
                                    QuizStatisticsCard(
                                        label = R.string.wrong,
                                        icon = R.drawable.ic_cancel,
                                        modifier = Modifier.weight(1f),
                                        contentColor = MaterialTheme.colorScheme.error,
                                        value = state.wrongQuestions.size.toString()
                                    )
                                    QuizStatisticsCard(
                                        label = R.string.skipped,
                                        icon = R.drawable.ic_forward,
                                        modifier = Modifier.weight(1f),
                                        contentColor = MaterialTheme.colorScheme.primary,
                                        value = state.skipQuestions.size.toString()
                                    )
                                    QuizStatisticsCard(
                                        label = R.string.time,
                                        icon = R.drawable.ic_time,
                                        modifier = Modifier.weight(1f),
                                        contentColor = MaterialTheme.colorScheme.primary,
                                        value = formatTime(state.quizTime)
                                    )
                                    QuizStatisticsCard(
                                        label = R.string.score,
                                        icon = R.drawable.ic_score,
                                        modifier = Modifier.weight(1f),
                                        contentColor = MaterialTheme.colorScheme.error,
                                        value = "${state.correctQuestions.size * 50}"
                                    )
                                    QuizStatisticsCard(
                                        label = R.string.coins,
                                        modifier = Modifier.weight(1f),
                                        icon = R.drawable.ic_coin,
                                        contentColor = Color.Unspecified,
                                        value = "${state.earnedCoin}"
                                    )
                                }
                                Spacer(Modifier.height(16.dp))
                                Text(
                                    text = state.motivationLine,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.surface,
                                        textAlign = TextAlign.Center
                                    )
                                )
                                Spacer(Modifier.height(16.dp))
                                PrimaryButton(
                                    text = stringResource(R.string.review_quiz),
                                    onClick = {
                                        viewModel.onAction(QuizAction.OnToggleReviewAnswersSection)
                                    },
                                    contentPadding = PaddingValues(vertical = 16.dp),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(Modifier.height(12.dp))
                                SecondaryButton(
                                    text = stringResource(R.string.play_again),
                                    onClick = {
                                        viewModel.onAction(QuizAction.OnRestartQuiz)
                                    },
                                    contentPadding = PaddingValues(vertical = 16.dp),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        } else {
                            val activeQuestion = state.quizQuestions[state.currentQuestionIndex]
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                contentPadding = PaddingValues(
                                    top = paddingValues.calculateTopPadding() + 8.dp,
                                    start = paddingValues.calculateStartPadding(layoutDirection) + 16.dp,
                                    end = paddingValues.calculateEndPadding(layoutDirection) + 16.dp,
                                    bottom = paddingValues.calculateTopPadding() + 16.dp,
                                )
                            ) {
                                item("DifficultyLevel") {
                                    val difficulty = DifficultyLevel.valueOf(difficultyLevel.replaceFirstChar { it.uppercase() })
                                    val containerColor by animateColorAsState(
                                        targetValue = when (difficulty){
                                            DifficultyLevel.Easy -> Green
                                            DifficultyLevel.Medium -> MaterialTheme.colorScheme.primary
                                            DifficultyLevel.Hard -> MaterialTheme.colorScheme.error
                                        }, animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(), label = "containerColor"
                                    )
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        ChipItem(
                                            isSelected = true, backgroundColor = containerColor,
                                            icon = difficulty.icon,
                                            label = difficulty.label
                                        )
                                    }
                                }
                                item("QuizProgressSection") {
                                    QuizProgressSection(
                                        modifier = Modifier.fillMaxWidth(),
                                        activeQuestion = state.currentQuestionIndex + 1,
                                        totalQuestions = state.quizQuestions.size,
                                        timeLeft = state.timeLeft,
                                        isQuestionLocked = state.isAnswerLocked
                                    )
                                }
                                item("QuestionContent") {
                                    QuestionContent(
                                        modifier = Modifier.fillMaxWidth(),
                                        question = activeQuestion
                                    )
                                }
                                quizQuestionOptions(
                                    modifier = Modifier.fillMaxWidth(),
                                    quizQuestion = activeQuestion,
                                    isAnswerLocked = state.isAnswerLocked,
                                    onOptionSelected = { answer ->
                                        viewModel.onAction(QuizAction.OnSelectAnswer(answer))
                                    }, options = state.currentQuestionOptions
                                )
                                item("next_question_button") {
                                    PrimaryButton(
                                        onClick = {
                                            viewModel.onAction(QuizAction.OnNextQuestion)
                                        }, enabled = state.isNextQuestionButtonEnabled,
                                        text = stringResource(R.string.next),
                                        modifier = Modifier.fillMaxWidth(),
                                        contentPadding = PaddingValues(vertical = 16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = state.showReviewAnswersSection,
            modifier = Modifier.fillMaxSize(),
            enter = slideInHorizontally(animationSpec = MaterialTheme.motionScheme.fastEffectsSpec()) { -it } + expandHorizontally(
                animationSpec = MaterialTheme.motionScheme.fastEffectsSpec()
            ) { -it } + fadeIn(animationSpec = MaterialTheme.motionScheme.fastEffectsSpec()),
            exit = slideOutHorizontally(animationSpec = MaterialTheme.motionScheme.fastEffectsSpec()) { -it } + shrinkHorizontally(
                animationSpec = MaterialTheme.motionScheme.fastEffectsSpec()
            ) { -it } + fadeOut(animationSpec = MaterialTheme.motionScheme.fastEffectsSpec())) {
            Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    CenterAlignedTopAppBar(
                        navigationIcon = {
                            IconButton(onClick = {
                                viewModel.onAction(QuizAction.OnToggleReviewAnswersSection)
                            }) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_back),
                                    contentDescription = null
                                )
                            }
                        },
                        title = {
                            Text(text = stringResource(R.string.review_answers))
                        }, actions = {
                            Text(
                                text = "${state.reviewAnswerIndex + 1}/${state.quizQuestions.size}",
                                modifier = Modifier.padding(end = 8.dp),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.surface
                                )
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
                    )
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.5.dp,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }, bottomBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .animateContentSize(animationSpec = MaterialTheme.motionScheme.fastEffectsSpec())
                        .navigationBarsPadding()
                ) {
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (state.reviewAnswerIndex > 0) {
                            PrimaryButton(
                                onClick = {
                                    viewModel.onAction(QuizAction.OnPreviousReviewAnswer)
                                },
                                modifier = Modifier.weight(1f),
                                leadingIcon = {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.ic_backward),
                                        contentDescription = null,
                                        modifier = Modifier.size(22.dp),
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                },
                                contentPadding = PaddingValues(vertical = 12.dp),
                                text = stringResource(R.string.previous)
                            )
                        }
                        if (state.reviewAnswerIndex != state.quizQuestions.size - 1) {
                            PrimaryButton(
                                onClick = {
                                    viewModel.onAction(QuizAction.OnNextReviewAnswer)
                                },
                                modifier = Modifier.weight(1f),
                                leadingIcon = {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.ic_forward),
                                        contentDescription = null,
                                        modifier = Modifier.size(22.dp),
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                },
                                contentPadding = PaddingValues(vertical = 12.dp),
                                text = stringResource(R.string.next)
                            )
                        }
                    }
                }
            }) { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(
                        top = paddingValues.calculateTopPadding() + 8.dp,
                        start = paddingValues.calculateStartPadding(layoutDirection) + 16.dp,
                        end = paddingValues.calculateEndPadding(layoutDirection) + 16.dp,
                        bottom = paddingValues.calculateTopPadding() + 16.dp,
                    )
                ) {
                    val currentReviewAnswer = state.quizQuestions[state.reviewAnswerIndex]
                    item("categoryDetail") {
                        val difficulty =
                            DifficultyLevel.valueOf(difficultyLevel.replaceFirstChar { it.uppercase() })
                        val containerColor by animateColorAsState(
                            targetValue = when (difficulty){
                                DifficultyLevel.Easy -> Green
                                DifficultyLevel.Medium -> MaterialTheme.colorScheme.primary
                                DifficultyLevel.Hard -> MaterialTheme.colorScheme.error
                            }, animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(), label = "containerColor"
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = categoryName,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.surface,
                                    textAlign = TextAlign.Center
                                )
                            )
                            ChipItem(
                                isSelected = true, backgroundColor = containerColor,
                                icon = difficulty.icon,
                                label = difficulty.label
                            )
                        }
                    }
                    item("questionContent") {
                        QuestionContent(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            showQuestionState = true,
                            question = currentReviewAnswer
                        )
                    }
                    reviewAnswerOptions(
                        modifier = Modifier.fillMaxWidth(),
                        quizQuestion = currentReviewAnswer
                    )
                }
            }
        }
    }
    if (state.showExitQuizDialog) {
        AppAlertDialog(
            onDismiss = {
                viewModel.onAction(QuizAction.ToggleExitQuizDialog)
            },
            title = stringResource(R.string.exit_quiz),
            message = stringResource(R.string.exit_quiz_desp),
            confirmText = stringResource(R.string.leave),
            dismissText = stringResource(R.string.cancel),
            onConfirmClick = {
                viewModel.onAction(QuizAction.OnConfirmQuitQuiz)
            }, onDismissClick = {
                viewModel.onAction(QuizAction.ToggleExitQuizDialog)
            }
        )
    }
}