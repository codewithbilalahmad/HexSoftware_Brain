package com.muhammad.brain.presentation.screens.home

import com.muhammad.brain.domain.model.DifficultyLevel
import com.muhammad.brain.domain.model.QuestionCount
import com.muhammad.brain.domain.model.QuizCategory

sealed interface HomeAction{
    data object GetQuizCategories : HomeAction
    data object OnShowQuizSetupSheet : HomeAction
    data object OnDismissQuizSetupSheet : HomeAction
    data object OnConfirmQuizSetupSheet : HomeAction
    data class OnQuizCategorySelected(val category: QuizCategory) : HomeAction
    data class OnSelectDifficultyLevel(val difficultyLevel: DifficultyLevel) : HomeAction
    data class OnSelectQuestionCount(val questionCount: QuestionCount) : HomeAction
}
