package com.muhammad.brain.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.brain.domain.repository.QuizRepository
import com.muhammad.brain.domain.utils.onError
import com.muhammad.brain.domain.utils.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val quizRepository: QuizRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()
    private val _events = Channel<HomeEvents>()
    val events = _events.receiveAsFlow()
    init {
        onAction(HomeAction.GetQuizCategories)
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.GetQuizCategories -> getQuizCategories()

            is HomeAction.OnQuizCategorySelected -> {
                _state.update {
                    it.copy(
                        selectedCategory = action.category,
                        showQuizSetupSheet = true
                    )
                }
            }

            is HomeAction.OnSelectDifficultyLevel -> {
                _state.update { it.copy(selectedDifficultyLevel = action.difficultyLevel) }
            }
            is HomeAction.OnSelectQuestionCount ->{
                _state.update { it.copy(selectedQuestionCount = action.questionCount) }
            }

            HomeAction.OnConfirmQuizSetupSheet -> {
                _state.update { it.copy(showQuizSetupSheet = false) }
                _events.trySend(HomeEvents.NavigateToQuizScreen)
            }
            HomeAction.OnDismissQuizSetupSheet ->{
                _state.update { it.copy(showQuizSetupSheet = false) }
            }
            HomeAction.OnShowQuizSetupSheet -> {
                _state.update { it.copy(showQuizSetupSheet = true) }
            }
        }
    }

    private fun getQuizCategories() {
        viewModelScope.launch {
            _state.update { it.copy(isCategoriesLoading = true) }
            quizRepository.getQuizCategories().onSuccess { data ->
                _state.update {
                    it.copy(
                        isCategoriesLoading = false,
                        categoriesError = null,
                        quizCategories = data
                    )
                }
            }.onError { error ->
                _state.update { it.copy(isCategoriesLoading = false, categoriesError = error) }
            }
        }
    }
}