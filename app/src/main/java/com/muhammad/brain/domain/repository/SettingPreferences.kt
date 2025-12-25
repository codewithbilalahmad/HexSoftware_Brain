package com.muhammad.brain.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingPreferences{
    fun observeCoins() : Flow<Int>
    suspend fun saveCoins(coins : Int)
}