package com.muhammad.brain.data.settings

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.muhammad.brain.domain.repository.SettingPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class SettingPreferencesImp(
    private val context: Context,
) : SettingPreferences {
    companion object{
        private val Context.settingPreferences by preferencesDataStore("settings_preferences")
        private const val COINS_KEY = "coins"
    }
    private val coinsKey = intPreferencesKey(COINS_KEY)
    override fun observeCoins(): Flow<Int> {
        return context.settingPreferences.data.map { preferences ->
            preferences[coinsKey] ?: 100
        }.distinctUntilChanged()
    }

    override suspend fun saveCoins(coins: Int) {
        context.settingPreferences.edit { preferences ->
            preferences[coinsKey] = coins
        }
    }
}