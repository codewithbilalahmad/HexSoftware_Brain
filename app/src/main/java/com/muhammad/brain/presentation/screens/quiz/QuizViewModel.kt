package com.muhammad.brain.presentation.screens.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.muhammad.brain.domain.model.QuizAnswerState
import com.muhammad.brain.domain.repository.QuizRepository
import com.muhammad.brain.domain.utils.onError
import com.muhammad.brain.domain.utils.onSuccess
import com.muhammad.brain.presentation.navigation.Destinations
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class QuizViewModel(
    savedStateHandle: SavedStateHandle,
    private val quizRepository: QuizRepository,
) : ViewModel() {
    private val arguments = savedStateHandle.toRoute<Destinations.QuizScreen>()
    private val _state = MutableStateFlow(QuizState())
    val state = _state.asStateFlow()
    private val _events = Channel<QuizEvent>()
    val events = _events.receiveAsFlow()
    private var questionTimerJob: Job? = null
    private var quizTimerJob : Job?=null
    init {
        onAction(QuizAction.GetQuizQuestions)
    }

    fun onAction(action: QuizAction) {
        when (action) {
            QuizAction.GetQuizQuestions -> getQuizQuestions()
            QuizAction.ToggleExitQuizDialog -> {
                _state.update { it.copy(showExitQuizDialog = !it.showExitQuizDialog) }
            }

            QuizAction.OnNextQuestion -> onNextQuestion()
            is QuizAction.OnSelectAnswer -> onSelectAnswer(action.answer)
            QuizAction.OnTimeFinished -> onTimeFinished()
            QuizAction.OnRestartQuiz -> onRestartQuiz()
            QuizAction.OnConfirmQuitQuiz -> {
                _state.update { it.copy(showExitQuizDialog = false) }
                _events.trySend(QuizEvent.OnNavigateBack)
            }
        }
    }

    private fun onRestartQuiz() {
        _state.update { QuizState() }
        onAction(QuizAction.GetQuizQuestions)
    }

    private fun onTimeFinished() {
        questionTimerJob?.cancel()
        val current = _state.value
        val index = current.currentQuestionIndex
        val question = current.quizQuestions[index]
        val updatedQuestion = question.copy(
            selectedAnswer = null, answerState = QuizAnswerState.Skipped
        )
        _state.update {
            it.copy(
                quizQuestions = it.quizQuestions.toMutableList().apply {
                    this[index] = updatedQuestion
                },
                isAnswerLocked = true,
                isNextQuestionButtonEnabled = true,
                skipQuestions = it.skipQuestions + question
            )
        }
    }

    private fun onNextQuestion() {
        val current = _state.value
        if (current.currentQuestionIndex + 1 >= current.questionCount) {
            quizTimerJob?.cancel()
            _state.update { it.copy(showQuizResult = true) }
            return
        }
        val nextIndex = current.currentQuestionIndex + 1
        val nextQuestion = current.quizQuestions[nextIndex]
        _state.update {
            it.copy(
                currentQuestionIndex = it.currentQuestionIndex + 1,
                isAnswerLocked = false,
                isNextQuestionButtonEnabled = false,
                timeLeft = 20,
                currentQuestionOptions = (nextQuestion.incorrectAnswers + nextQuestion.correctAnswer).shuffled()
            )
        }
        startQuestionTimer()
    }

    private fun onSelectAnswer(selected: String) {
        questionTimerJob?.cancel()
        val current = _state.value
        val index = current.currentQuestionIndex
        val question = current.quizQuestions[index]
        val isCorrect = selected == question.correctAnswer
        val updatedQuestion = question.copy(
            selectedAnswer = selected,
            answerState = if (isCorrect) QuizAnswerState.Correct else QuizAnswerState.Wrong
        )
        _state.update {
            it.copy(
                quizQuestions = it.quizQuestions.toMutableList().apply {
                    this[index] = updatedQuestion
                },
                isAnswerLocked = true,
                isNextQuestionButtonEnabled = true
            )
        }
        if (isCorrect) {
            _state.update { it.copy(correctQuestions = it.correctQuestions + question) }
        } else {
            _state.update { it.copy(wrongQuestions = it.wrongQuestions + question) }
        }
    }

    private fun getQuizQuestions() {
        viewModelScope.launch {
            _state.update { it.copy(isQuizQuestionLoading = true) }
            quizRepository.getQuizQuestions(
                categoryId = arguments.categoryId,
                difficulty = arguments.difficulty,
                questionCount = arguments.questionCount
            ).onSuccess { data ->
                _state.update {
                    val firstQuestion = data.firstOrNull()
                    it.copy(
                        isQuizQuestionLoading = false,
                        quizQuestionError = null, questionCount = arguments.questionCount,
                        quizQuestions = data,
                        currentQuestionOptions = if (firstQuestion != null) {
                            (firstQuestion.incorrectAnswers + firstQuestion.correctAnswer).shuffled()
                        } else emptyList()
                    )
                }
                startQuestionTimer()
                startQuizTimer()
            }.onError { error ->
                _state.update { it.copy(isQuizQuestionLoading = false, quizQuestionError = error) }
            }
        }
    }
    private fun startQuizTimer(){
        quizTimerJob?.cancel()
        quizTimerJob = viewModelScope.launch {
            while (isActive){
                _state.update { it.copy(quizTime = it.quizTime + 1) }
                delay(1000L)
            }
        }
    }
    private fun startQuestionTimer() {
        questionTimerJob?.cancel()
        questionTimerJob = viewModelScope.launch {
            for (second in 20 downTo 0) {
                _state.update { it.copy(timeLeft = second) }
                delay(1000L)
                if (second == 0 && !state.value.isAnswerLocked) {
                    onAction(QuizAction.OnTimeFinished)
                    return@launch
                }
            }
        }
    }
}