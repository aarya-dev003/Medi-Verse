package com.example.medi_verse.App

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medi_verse.R
import com.example.medi_verse.repository.RemoteRepo
import com.example.medi_verse.utils.SessionManager
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, remoteRepo: RemoteRepo, sessionManager: SessionManager) {
    LaunchedEffect(Unit) {
        delay(1000)
        val result = sessionManager.getLoginContext().toString()
        val token = sessionManager.getJwtToken()
        when {
            result == "club" && token != null -> {
                navController.navigate("ClubAdminMainScreen") {
                    popUpTo("SplashScreen") { inclusive = true }
                }
            }
            result == "admin" && token != null -> {
                navController.navigate("CollegeAdminMainScreen") {
                    popUpTo("SplashScreen") { inclusive = true }
                }
            }
            result == "user" && token != null -> {
                navController.navigate("HomeMainScreen") {
                    popUpTo("SplashScreen") { inclusive = true }
                }
            }
            else -> {
                navController.navigate("AppScreens") {
                    popUpTo("SplashScreen") { inclusive = true }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.finaliconlogo),
            contentDescription = "Logo",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
        )
    }
}
