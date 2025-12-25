package com.muhammad.brain.presentation.screens.home

import com.muhammad.brain.domain.model.DifficultyLevel
import com.muhammad.brain.domain.model.QuestionCount
import com.muhammad.brain.domain.model.QuizCategory


data class HomeState(
    val quizCategories: List<QuizCategory> = emptyList(),
    val isCategoriesLoading: Boolean = false,
    val categoriesError: String? = null,
    val coins : Int = 0,
    val selectedCategory: QuizCategory? = null,
    val showQuizSetupSheet : Boolean = false,
    val selectedDifficultyLevel: DifficultyLevel = DifficultyLevel.Easy,
    val selectedQuestionCount: QuestionCount = QuestionCount.Ten
)