package com.example.medi_verse.utils

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class SessionManager(val context: Context) {
    private val USER_PREFERENCES_NAME = "session_manager"

    // Define constants for preference keys
    private val CONTEXT_KEY = stringPreferencesKey("context_key")
    private val JWT_TOKEN_KEY = stringPreferencesKey("jwt_token_key")
    private val USER_NAME_KEY = stringPreferencesKey("user_name")
    private val USER_EMAIL_KEY = stringPreferencesKey("user_email")

    private val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    // Function to create login context
    suspend fun createLoginContext(applicationContext: String) {
        try {
            context.dataStore.edit { preferences ->
                preferences[CONTEXT_KEY] = applicationContext
            }
        } catch (e: Exception) {
            Log.e("SessionManager", "Error creating login context: ${e.message}")
        }
    }

    // Function to retrieve login context
    suspend fun getLoginContext(): String? {
        return try {
            val preferences = context.dataStore.data.first()
            preferences[CONTEXT_KEY]
        } catch (e: Exception) {
            Log.e("SessionManager", "Error retrieving login context: ${e.message}")
            null
        }
    }

    // Function to update session
    suspend fun updateSession(token: String, name: String, email: String) {
        try {
            context.dataStore.edit { preferences ->
                preferences[JWT_TOKEN_KEY] = token
                preferences[USER_NAME_KEY] = name
                preferences[USER_EMAIL_KEY] = email
            }
            Log.d("Session Token", token)
        } catch (e: Exception) {
            Log.e("SessionManager", "Error updating session: ${e.message}")
        }
    }

    // Function to retrieve JWT token
    suspend fun getJwtToken(): String? {
        return try {
            val preferences = context.dataStore.data.map {
                it[JWT_TOKEN_KEY]
            }.firstOrNull()

            preferences
        } catch (e: Exception) {
            Log.e("SessionManager", "Error retrieving JWT token: ${e.message}")
            null
        }
    }

    // Function to retrieve current username
    suspend fun getCurrentUsername(): String? {
        return try {
            val preferences = context.dataStore.data.first()
            preferences[USER_NAME_KEY]
        } catch (e: Exception) {
            Log.e("SessionManager", "Error retrieving current username: ${e.message}")
            null
        }
    }

    // Function to retrieve current email
    suspend fun getCurrentEmail(): String? {
        return try {
            val preferences = context.dataStore.data.first()
            preferences[USER_EMAIL_KEY]
        } catch (e: Exception) {
            Log.e("SessionManager", "Error retrieving current email: ${e.message}")
            null
        }
    }

    // Function to clear session (logout)
    suspend fun logout() {
        try {
            context.dataStore.edit { preferences ->
                preferences.remove(JWT_TOKEN_KEY)
                preferences.remove(USER_NAME_KEY)
                preferences.remove(USER_EMAIL_KEY)
            }
        } catch (e: Exception) {
            Log.e("SessionManager", "Error clearing session: ${e.message}")
        }
    }
}
