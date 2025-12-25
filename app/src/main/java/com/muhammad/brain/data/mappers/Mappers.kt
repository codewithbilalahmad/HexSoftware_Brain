package com.muhammad.brain.data.mappers

import com.muhammad.brain.data.dto.QuizCategoryDto
import com.muhammad.brain.data.dto.QuizQuestionDto
import com.muhammad.brain.domain.model.QuizCategory
import com.muhammad.brain.domain.model.QuizQuestion
import com.muhammad.brain.utils.createBeautifulGradient
import com.muhammad.brain.utils.getCategoryImageByName

fun QuizQuestionDto.toQuizQuestion(id : Int): QuizQuestion {
    return QuizQuestion(
        id = id,
        category = category,
        correctAnswer = correctAnswer,
        difficulty = difficulty,
        incorrectAnswers = incorrectAnswers,
        question = question, options = (incorrectAnswers + correctAnswer).shuffled(),
        type = type, brushColors = createBeautifulGradient()
    )
}

fun QuizCategoryDto.toQuizCategory(): QuizCategory {
    val cleanName = name.substringAfter(": ", name)
    return QuizCategory(
        id = id,
        name = cleanName,
        icon = getCategoryImageByName(cleanName), brushColors = createBeautifulGradient()
    )
}