package com.example.medi_verse.App

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.medi_verse.App.AppNavigation
import com.example.medi_verse.RetrofitBuilder.provideApiService
import com.example.medi_verse.RetrofitBuilder.provideMoshi
import com.example.medi_verse.data.remote.ApiService
import com.example.medi_verse.repository.RemoteRepoImpl
import com.example.medi_verse.utils.SessionManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sessionManager = SessionManager(context = this)

        // Create ApiService instance using the Retrofit builder function
        val apiService = provideApiService(provideMoshi())

        val remoteRepo = RemoteRepoImpl(apiService, sessionManager)
        setContent {
            AppNavigation(context = this, remoteRepo)
        }

    }
}
