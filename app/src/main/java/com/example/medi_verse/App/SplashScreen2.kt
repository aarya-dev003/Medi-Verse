package com.example.medi_verse.App

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
@Composable
fun SplashScreen (){
    LaunchedEffect(key1 = true) {
        delay(2000)
    }
}