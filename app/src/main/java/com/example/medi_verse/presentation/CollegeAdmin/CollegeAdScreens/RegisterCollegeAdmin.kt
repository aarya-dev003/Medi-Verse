package com.example.medi_verse.presentation.CollegeAdmin.CollegeAdScreens

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.medi_verse.App.AppScreens
import com.example.medi_verse.R
import com.example.medi_verse.data.remote.model.ClubRegisterRequest
import com.example.medi_verse.data.remote.model.LoginRequest
import com.example.medi_verse.data.remote.model.Post
import com.example.medi_verse.data.remote.model.RegisterRequest

import com.example.medi_verse.presentation.CollegeAdmin.CollegeAdNav.CollegeAdBottomBarScreen

import com.example.medi_verse.presentation.Formator.uploadImageToFirebase

import com.example.medi_verse.presentation.Student.StNav.HomeBottomBarScreen
import com.example.medi_verse.repository.RemoteRepo
import com.example.medi_verse.ui.theme.BackgroundColor
import com.example.medi_verse.utils.Result
import com.example.medi_verse.utils.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterCollegeAdmin(
    navController: NavController,
    remoteRepo: RemoteRepo,
    context: Context,
    sessionManager: SessionManager,
    AppnavController: NavController
) {
    val createClubResult = remember { mutableStateOf<Result<String>?>(null) }
    var check by remember { mutableStateOf(false) }
    val newusernamevalue = remember { mutableStateOf("") }
    val newuserusernamevalue = remember { mutableStateOf("") }
    val newuseremailvalue = remember { mutableStateOf("") }
    val newuserpasswordvalue = remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor),
        contentAlignment = Alignment.TopCenter,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 0.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        sessionManager.logout()
                    }
                    AppnavController.navigate(AppScreens.Decision.route) {
                        popUpTo(AppScreens.Decision.route) {
                            inclusive = true
                            AppnavController.popBackStack()
                        }
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
                    .size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Logout",
                    tint = Color.Black
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 50.dp)
        ) {
            Text(
                text = "Register Club Admin",
                color = Color.Black,
                fontFamily = FontFamily.Monospace,
                fontSize = 25.sp,
                modifier = Modifier.padding(top = 10.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier.size(width = 250.dp, height = 120.dp),
                contentAlignment = Alignment.Center
            ) {
                val painter: Painter = if (imageUri != null) {
                    rememberAsyncImagePainter(imageUri)
                } else {
                    painterResource(id = R.drawable.profileimage)
                }
                Image(
                    painter = painter,
                    contentDescription = "Clickable image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { galleryLauncher.launch("image/*") }
                        .size(140.dp, 120.dp)
                )
            }

            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                value = newusernamevalue.value,
                onValueChange = { newusernamevalue.value = it },
                label = { Text(text = "Enter name") },
                modifier = Modifier.padding(vertical = 15.dp),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    containerColor = Color.White,
                ),
                textStyle = TextStyle(color = Color.Black),
                shape = RoundedCornerShape(12.dp)
            )
            TextField(
                value = newuserusernamevalue.value,
                onValueChange = { newuserusernamevalue.value = it },
                label = { Text(text = "Enter user name") },
                modifier = Modifier.padding(vertical = 15.dp),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    containerColor = Color.White,
                ),
                textStyle = TextStyle(color = Color.Black),
                shape = RoundedCornerShape(12.dp)
            )
            TextField(
                value = newuseremailvalue.value,
                onValueChange = { newuseremailvalue.value = it },
                label = { Text(text = "Enter email") },
                modifier = Modifier.padding(vertical = 15.dp),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    containerColor = Color.White,
                ),
                textStyle = TextStyle(color = Color.Black),
                shape = RoundedCornerShape(12.dp)
            )
            TextField(
                value = newuserpasswordvalue.value,
                onValueChange = { newuserpasswordvalue.value = it },
                label = { Text(text = "Enter password") },
                modifier = Modifier.padding(vertical = 15.dp),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    containerColor = Color.White,
                ),
                textStyle = TextStyle(color = Color.Black),
                shape = RoundedCornerShape(12.dp)
            )

            Button(
                onClick = {
                    if (imageUri == null) {
                        Toast.makeText(context, "Please select an image", Toast.LENGTH_SHORT).show()
                    } else {
                        GlobalScope.launch(Dispatchers.Main) {
                            val imageUrl = uploadImageToFirebase(
                                context,
                                imageUri!!,
                                newusernamevalue.value,
                                "club_admin"
                            )
                            imageUrl?.let { url ->
                                val registerClub = ClubRegisterRequest(
                                    name = newusernamevalue.value,
                                    username = newuserusernamevalue.value,
                                    email = newuseremailvalue.value,
                                    password = newuserpasswordvalue.value,
                                    imageUrl = url
                                )
                                CoroutineScope(Dispatchers.IO).launch {
                                    val result = remoteRepo.createClubAdmin(registerClub)
                                    createClubResult.value = result
                                }
                            }
                        }
                    }
                    navController.navigate(CollegeAdBottomBarScreen.Home.route) {
                        popUpTo(CollegeAdBottomBarScreen.Home.route) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier.size(width = 150.dp, height = 50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                )
            ) {
                Text(text = "Register")
            }

            createClubResult.value?.let { result ->
                if (result is Result.Success) {
                    navController.navigate(AppScreens.CollegeAdminMainScreen.route) {
                        popUpTo(AppScreens.CollegeAdminMainScreen.route) {
                            inclusive = true
                        }
                    }
                } else if (result is Result.Error) {
                    Toast.makeText(context, result.errorMessage.toString().trim(), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Some Unexpected Error Occurred", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
