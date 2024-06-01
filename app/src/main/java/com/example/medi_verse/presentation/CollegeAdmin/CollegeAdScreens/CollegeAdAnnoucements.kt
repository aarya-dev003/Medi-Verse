package com.example.medi_verse.presentation.CollegeAdmin.CollegeAdScreens

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medi_verse.App.AppScreens
import com.example.medi_verse.presentation.CollegeAdmin.CollegeAdNav.CollegeAdBottomBarScreen
import com.example.medi_verse.data.remote.model.Announcement
import com.example.medi_verse.repository.RemoteRepo
import com.example.medi_verse.ui.theme.BackgroundColor
import com.example.medi_verse.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollegeAdAnnoucements(context:Context,navController: NavController, remoteRepo: RemoteRepo) {
    val createAnnouncementResult = remember { mutableStateOf<Result<String>?>(null) }
    Box(modifier = Modifier
        .background(BackgroundColor)
        .fillMaxSize(), contentAlignment = Alignment.TopCenter){
        Column {
            Text(
                text = "Annoucements",
                color = Color(0xFF134074),
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
            )
            Text(
                text = "Here you can make announcements for students",
                color = Color.Black,
                fontSize = 15.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, bottom = 30.dp)
            )

            var userdes by remember { mutableStateOf("") }
            TextField(
                value = userdes,
                onValueChange = { userdes = it },
                placeholder = { Text("Enter Announcement") },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Black,
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxHeight(.5f)
                    .fillMaxWidth(.9f).padding(40.dp, top = 30.dp),
                shape = RoundedCornerShape(5.dp),
            )
            Button(
                modifier = Modifier.background(Color.Transparent).padding(start = 140.dp,top=10.dp),
                onClick = {
                    val announcement = Announcement(
                        description = userdes,
                        timestamp = System.currentTimeMillis()
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        val announcementReq = remoteRepo.createAnnouncement(announcement)
                        createAnnouncementResult.value = announcementReq
                    }
                    Toast.makeText(context, "Announcement posted successfully", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Post")
            }
        }
        createAnnouncementResult.value?.let { result ->
            if (result is Result.Success) {
                navController.navigate(AppScreens.CollegeAdminMainScreen.route) {
                    popUpTo(AppScreens.CollegeAdminMainScreen.route) {
                        inclusive = true
                    }
                }
            } else if (result is Result.Error) {
                Toast.makeText(context, result.errorMessage.toString().trim(), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Some Unexpected Error Occured", Toast.LENGTH_SHORT).show()
            }
        }


    }
    BackHandler {
        navController.navigate(route = CollegeAdBottomBarScreen.Home.route) {
            popUpTo(route = CollegeAdBottomBarScreen.Home.route) {
                inclusive = true
            }
        }
    }}