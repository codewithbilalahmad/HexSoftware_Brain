package com.muhammad.brain.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.muhammad.brain.presentation.screens.home.HomeScreen
import com.muhammad.brain.presentation.screens.leader_board.LeaderboardScreen
import com.muhammad.brain.presentation.screens.quiz.QuizScreen

@Composable
fun AppNavigation(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Destinations.HomeScreen, enterTransition = {
        slideInHorizontally { it } + expandHorizontally { it }
    }, exitTransition = {
        slideOutHorizontally { it } + shrinkHorizontally { it }
    }, popExitTransition = {
        slideOutHorizontally { -it } + shrinkHorizontally { -it }
    }, popEnterTransition = {
        slideInHorizontally { -it } + expandHorizontally { -it }
    }) {
        composable<Destinations.HomeScreen> {
            HomeScreen(navHostController = navHostController)
        }
        composable<Destinations.QuizScreen> {
            val arguments = it.toRoute<Destinations.QuizScreen>()
            val categoryName = arguments.categoryName
            val difficultyLevel = arguments.difficulty
            QuizScreen(navHostController = navHostController, categoryName = categoryName, difficultyLevel = difficultyLevel)
        }
        composable<Destinations.LeaderboardScreen>{
            LeaderboardScreen(navHostController = navHostController)
        }
    }
}