package com.muhammad.brain.di

import com.muhammad.brain.data.network.QuizNetworkImp
import com.muhammad.brain.data.network.client.HttpClientFactory
import com.muhammad.brain.data.repository.QuizRepositoryImp
import com.muhammad.brain.domain.network.QuizNetwork
import com.muhammad.brain.domain.repository.QuizRepository
import com.muhammad.brain.presentation.screens.home.HomeViewModel
import com.muhammad.brain.presentation.screens.leader_board.LeaderboardViewModel
import com.muhammad.brain.presentation.screens.quiz.QuizViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.createClient() }
    single { QuizNetworkImp(get()) }.bind<QuizNetwork>()
    single { QuizRepositoryImp(get()) }.bind<QuizRepository>()
    viewModelOf(::HomeViewModel)
    viewModelOf(::QuizViewModel)
    viewModelOf(::LeaderboardViewModel)
}