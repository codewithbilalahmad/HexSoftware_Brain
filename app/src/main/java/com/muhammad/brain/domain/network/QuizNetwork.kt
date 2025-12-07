package com.muhammad.brain.domain.network

import com.muhammad.brain.domain.model.QuizCategory
import com.muhammad.brain.domain.model.QuizQuestion
import com.muhammad.brain.domain.utils.Result

interface QuizNetwork {
    suspend fun getQuizCategories(): Result<List<QuizCategory>>
    suspend fun getQuizQuestions(
        categoryId: Int,
        difficulty: String,
        questionCount: Int
    ): Result<List<QuizQuestion>>
}