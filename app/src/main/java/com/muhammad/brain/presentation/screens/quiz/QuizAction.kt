package com.muhammad.brain.presentation.screens.quiz

sealed interface QuizAction{
    data object GetQuizQuestions : QuizAction
    data object ToggleExitQuizDialog : QuizAction
    data class OnSelectAnswer(val answer : String) : QuizAction
    data object OnTimeFinished : QuizAction
    data object OnNextQuestion : QuizAction
    data object OnRestartQuiz : QuizAction
    data object OnConfirmQuitQuiz : QuizAction
}