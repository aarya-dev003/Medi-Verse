package com.example.medi_verse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.medi_verse.App.AppNavigation
import com.example.medi_verse.RetrofitBuilder.provideApiService
import com.example.medi_verse.RetrofitBuilder.provideMoshi
import com.example.medi_verse.repository.RemoteRepoImpl
import com.example.medi_verse.ui.theme.MediVerseTheme
import com.example.medi_verse.utils.SessionManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sessionManager = SessionManager(context = this)

        // Create ApiService instance using the Retrofit builder function
        val apiService = provideApiService(provideMoshi())

        val remoteRepo = RemoteRepoImpl(apiService, sessionManager)
        setContent {
            AppNavigation(context = this,remoteRepo)
        }
    }
}