package com.muhammad.brain.presentation.screens.quiz

import com.muhammad.brain.domain.model.QuizQuestion
import com.muhammad.brain.utils.getRandomMotivationLine

data class QuizState(
    val quizQuestions: List<QuizQuestion> = emptyList(),
    val attemptedQuestions: List<QuizQuestion> = emptyList(),
    val correctQuestions : List<QuizQuestion> = emptyList(),
    val wrongQuestions : List<QuizQuestion> = emptyList(),
    val skipQuestions : List<QuizQuestion> = emptyList(),
    val isQuizQuestionLoading : Boolean = false,
    val currentQuestionOptions : List<String> = emptyList(),
    val quizQuestionError : String?=null,
    val currentQuestionIndex: Int = 0,
    val questionCount : Int = 0,
    val showExitQuizDialog : Boolean = false,
    val isAnswerLocked : Boolean = false,
    val timeLeft : Int = 20,
    val quizTime : Int = 0,
    val isNextQuestionButtonEnabled : Boolean = false,
    val showQuizResult : Boolean = false,
    val motivationLine : String = getRandomMotivationLine()
)

