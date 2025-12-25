package com.muhammad.brain.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.muhammad.brain.domain.model.QuestionCount
import com.muhammad.brain.domain.utils.ObserveAsEvents
import com.muhammad.brain.presentation.components.AppLoading
import com.muhammad.brain.presentation.components.ChipItem
import com.muhammad.brain.presentation.components.PrimaryButton
import com.muhammad.brain.presentation.navigation.Destinations
import com.muhammad.brain.presentation.screens.home.components.CategoryHeader
import com.muhammad.brain.presentation.screens.home.components.HomeHeader
import com.muhammad.brain.presentation.screens.home.components.QuizCategoryCard
import com.muhammad.brain.presentation.screens.home.components.QuizFeatureCategoriesSection
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navHostController: NavHostController, viewModel: HomeViewModel = koinViewModel()) {
    val layoutDirection = LocalLayoutDirection.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            HomeEvents.NavigateToQuizScreen -> {
                navHostController.navigate(
                    Destinations.QuizScreen(
                        categoryId = state.selectedCategory?.id ?: return@ObserveAsEvents,
                        categoryName = state.selectedCategory?.name ?: return@ObserveAsEvents,
                        difficulty = state.selectedDifficultyLevel.name.lowercase(),
                        questionCount = state.selectedQuestionCount.count
                    )
                )
            }
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        HomeHeader(onLeaderboardClick = {
            navHostController.navigate(Destinations.LeaderboardScreen)
        }, coins = state.coins)
    }) { paddingValues ->
        when {
            state.isCategoriesLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    AppLoading()
                }
            }

            state.categoriesError != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(vertical = 16.dp, horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_failed),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                    Text(
                        text = state.categoriesError!!,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(
                        start = paddingValues.calculateStartPadding(layoutDirection) + 16.dp,
                        end = paddingValues.calculateEndPadding(layoutDirection) + 16.dp,
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding() + 16.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    stickyHeader("featured_categories_header") {
                        CategoryHeader(
                            icon = R.drawable.ic_featured,
                            label = R.string.featured_categories,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    item("featured_categories") {
                        QuizFeatureCategoriesSection(
                            featuredCategories = state.quizCategories.shuffled().take(3),
                            modifier = Modifier.fillMaxWidth(), onCategoryClick = { category ->
                                viewModel.onAction(HomeAction.OnQuizCategorySelected(category))
                            }
                        )
                    }
                    stickyHeader("all_categories_header") {
                        CategoryHeader(
                            icon = R.drawable.ic_quiz, label = R.string.all_categories,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    items(state.quizCategories, key = { it.id }) { category ->
                        QuizCategoryCard(
                            modifier = Modifier.fillMaxWidth(),
                            category = category,
                            onClick = {
                                viewModel.onAction(HomeAction.OnQuizCategorySelected(category))
                            })
                    }
                }
            }
        }
    }
    if (state.showQuizSetupSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.onAction(HomeAction.OnDismissQuizSetupSheet)
            },
            containerColor = MaterialTheme.colorScheme.background,
            dragHandle = {},
            sheetState = bottomSheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 24.dp, top = 16.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    IconButton(onClick = {
                        viewModel.onAction(HomeAction.OnDismissQuizSetupSheet)
                    }, modifier = Modifier.align(Alignment.CenterStart)) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_cancel),
                            contentDescription = null
                        )
                    }
                    Text(
                        text = stringResource(R.string.quiz_setup),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.difficulty),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                )
                Spacer(Modifier.height(16.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(DifficultyLevel.entries, key = { it.name }) { level ->
                        val isSelected = level == state.selectedDifficultyLevel
                        ChipItem(
                            icon = level.icon,
                            isSelected = isSelected,
                            label = level.label,
                            onClick = {
                                viewModel.onAction(HomeAction.OnSelectDifficultyLevel(level))
                            })
                    }
                }
                Spacer(Modifier.height(24.dp))
                Text(
                    text = stringResource(R.string.number_of_questions),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                )
                Spacer(Modifier.height(16.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(QuestionCount.entries, key = { it.name }) { count ->
                        val isSelected = count == state.selectedQuestionCount
                        ChipItem(isSelected = isSelected, label = count.label, onClick = {
                            viewModel.onAction(HomeAction.OnSelectQuestionCount(count))
                        })
                    }
                }
                Spacer(Modifier.height(24.dp))
                PrimaryButton(
                    text = stringResource(R.string.start_quiz),
                    onClick = {
                        viewModel.onAction(HomeAction.OnConfirmQuizSetupSheet)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 16.dp)
                )
            }
        }
    }
}