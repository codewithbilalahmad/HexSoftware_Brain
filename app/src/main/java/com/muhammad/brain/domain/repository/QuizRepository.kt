package com.muhammad.brain.domain.repository

import com.muhammad.brain.domain.model.QuizCategory
import com.muhammad.brain.domain.model.QuizQuestion
import com.muhammad.brain.domain.utils.Result

interface QuizRepository{
    suspend fun getQuizCategories(): Result<List<QuizCategory>>
    suspend fun getQuizQuestions(
        categoryId: Int,
        difficulty: String,
        questionCount: Int
    ): Result<List<QuizQuestion>>
}