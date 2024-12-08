package org.marino.tfgpagao.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val tokenKey = stringPreferencesKey("auth_token")

    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    val getAuthToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[tokenKey]
        }

    suspend fun clearAuthToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(tokenKey)
        }
    }
}