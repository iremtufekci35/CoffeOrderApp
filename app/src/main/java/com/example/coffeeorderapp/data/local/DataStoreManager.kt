package com.example.coffeeorderapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.dataStore

    companion object {
        val USER_EMAIL = stringPreferencesKey("user_email")
        val LOGIN_KEY = booleanPreferencesKey("is_logged_in")
    }

    suspend fun saveEmail(email: String) {
        dataStore.edit { prefs ->
            prefs[USER_EMAIL] = email
        }
    }

    val getEmail: Flow<String?> = dataStore.data
        .map { prefs -> prefs[USER_EMAIL] }

    suspend fun setLoggedIn(loggedIn: Boolean) {
        dataStore.edit { prefs ->
            prefs[LOGIN_KEY] = loggedIn
        }
    }

    val isLoggedIn: Flow<Boolean> = dataStore.data
        .map { prefs -> prefs[LOGIN_KEY] ?: false }

    suspend fun logout() {
        dataStore.edit { prefs ->
            prefs.remove(USER_EMAIL)
            prefs[LOGIN_KEY] = false
        }
    }
}
