package com.muhammad.brain.data.repository

import com.muhammad.brain.domain.model.QuizCategory
import com.muhammad.brain.domain.model.QuizQuestion
import com.muhammad.brain.domain.network.QuizNetwork
import com.muhammad.brain.domain.utils.Result
import com.muhammad.brain.domain.repository.QuizRepository

class QuizRepositoryImp(
    private val quizNetwork: QuizNetwork,
) : QuizRepository {
    override suspend fun getQuizCategories(): Result<List<QuizCategory>> {
        return quizNetwork.getQuizCategories()
    }

    override suspend fun getQuizQuestions(
        categoryId: Int,
        difficulty: String,
        questionCount: Int,
    ): Result<List<QuizQuestion>> {
        return quizNetwork.getQuizQuestions(
            categoryId = categoryId,
            difficulty = difficulty,
            questionCount = questionCount
        )
    }
}