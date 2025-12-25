package com.muhammad.brain.data.network

import com.muhammad.brain.data.dto.QuizCategoryResponseDto
import com.muhammad.brain.data.dto.QuizQuestionResponseDto
import com.muhammad.brain.data.mappers.toQuizCategory
import com.muhammad.brain.data.mappers.toQuizQuestion
import com.muhammad.brain.data.network.client.get
import com.muhammad.brain.domain.model.QuizCategory
import com.muhammad.brain.domain.model.QuizQuestion
import com.muhammad.brain.domain.network.QuizNetwork
import com.muhammad.brain.domain.utils.Result
import com.muhammad.brain.domain.utils.map
import io.ktor.client.HttpClient

class QuizNetworkImp(
    private val httpClient: HttpClient,
) : QuizNetwork {
    override suspend fun getQuizCategories(): Result<List<QuizCategory>> {
        return httpClient.get<QuizCategoryResponseDto>("api_category.php").map { response ->
            response.trivia_categories.map { it.toQuizCategory() }
        }
    }

    override suspend fun getQuizQuestions(
        categoryId: Int,
        difficulty: String,
        questionCount: Int,
    ): Result<List<QuizQuestion>> {
        return httpClient.get<QuizQuestionResponseDto>(
            route = "api.php", queryParameters = mapOf(
                "amount" to questionCount,
                "category" to categoryId,
                "difficulty" to difficulty,
                "type" to "multiple"
            )
        ).map { response ->
            response.questions.mapIndexed { index, question ->
                question.toQuizQuestion(
                    id = index + 1
                )
            }
        }
    }
}