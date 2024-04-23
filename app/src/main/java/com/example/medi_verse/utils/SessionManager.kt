package com.example.medi_verse.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first


class SessionManager(val context : Context) {
    private val USER_PREFERENCES_NAME = "session_manager"

    private val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    suspend fun createLoginContext(applicationContext : String){
        val contextKey = stringPreferencesKey("CONTEXT_KEY")
        context.dataStore.edit{preferences->
            preferences[contextKey] = applicationContext
        }
    }

    suspend fun getLoginContext(): String? {
        val contextKey = stringPreferencesKey("CONTEXT_KEY")
        val preferences = context.dataStore.data.first()

        return preferences[contextKey]
    }

    suspend fun updateSession(token : String, name : String , email: String){
        val jwtTokenKey = stringPreferencesKey("JWT_TOKEN_KEY")
        val nameKey = stringPreferencesKey("USER_NAME")
        val emailKey = stringPreferencesKey("USER_EMAIL")
        context.dataStore.edit{preferences->
            preferences[jwtTokenKey] = token
            preferences[nameKey] = name
            preferences[emailKey] = email
        }
    }

    suspend fun getJwtToken() : String?{
        val jwtTokenKey = stringPreferencesKey("JWT_TOKEN_KEY")
        val preferences = context.dataStore.data.first()

        return preferences[jwtTokenKey]
    }

    suspend fun getCurrentUsername(): String?{
        val nameKey = stringPreferencesKey("USER_NAME")
        val preferences = context.dataStore.data.first()

        return preferences[nameKey]
    }

    suspend fun getCurrentEmail(): String?{
        val emailKey = stringPreferencesKey("USER_EMAIL")
        val preferences = context.dataStore.data.first()

        return preferences[emailKey]
    }

    suspend fun logout(){
        context.dataStore.edit {
            it.clear()
        }
    }

}