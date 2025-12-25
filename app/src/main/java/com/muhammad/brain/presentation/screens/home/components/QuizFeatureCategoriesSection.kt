package com.muhammad.brain.presentation.screens.home.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.muhammad.brain.domain.model.QuizCategory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun QuizFeatureCategoriesSection(
    modifier: Modifier = Modifier,
    onCategoryClick : (QuizCategory) -> Unit,
    featuredCategories: List<QuizCategory>,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState { featuredCategories.size }
    LaunchedEffect(true) {
        while (true) {
            delay(5000)
            scope.launch {
                val nextPage = (pagerState.currentPage + 1) % featuredCategories.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState,
            pageSpacing = 16.dp
        ) { page ->
            val category = featuredCategories[page]
            QuizFeatureCategoryCard(category = category, onClick = {
                onCategoryClick(category)
            })
        }
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                repeat(featuredCategories.size) { index ->
                    val isCurrent = pagerState.currentPage == index
                    val color by animateColorAsState(
                        targetValue = if (isCurrent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                        animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(),
                        label = "color"
                    )
                    val width by animateDpAsState(
                        targetValue = if (isCurrent) 24.dp else 12.dp,
                        animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(),
                        label = "width"
                    )
                    Box(
                        modifier = Modifier
                            .size(width = width, height = 6.dp)
                            .clip(CircleShape)
                            .background(color)
                    )
                }
            }
        }
    }
}