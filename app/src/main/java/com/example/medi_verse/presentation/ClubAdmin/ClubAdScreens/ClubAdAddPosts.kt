package com.example.medi_verse.presentation.ClubAdmin.ClubAdScreens

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.medi_verse.App.AppScreens
import com.example.medi_verse.presentation.ClubAdmin.ClubAdNav.ClubAdminBottomBarScreen
import com.example.medi_verse.R
import com.example.medi_verse.data.remote.model.Post
import com.example.medi_verse.presentation.Formator.uploadImageToFirebase
import com.example.medi_verse.repository.RemoteRepo
import com.example.medi_verse.ui.theme.BackgroundColor
import com.example.medi_verse.utils.Result
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubAdAddPosts (context: Context, navController: NavController, remoteRepo: RemoteRepo) {
    val createPostResult = remember { mutableStateOf<Result<String>?>(null) }
    Box(modifier = Modifier
        .background(BackgroundColor)
        .fillMaxSize(), contentAlignment = Alignment.TopCenter){
        var imageUri by remember { mutableStateOf<Uri?>(null) }
        val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                imageUri = uri
            }
        )
        var check by remember { mutableStateOf(false) }
        Column (
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            val cardColors = if (check) {
                CardDefaults.cardColors(containerColor = Color.Transparent)
            } else {
                CardDefaults.cardColors(containerColor = Color.White)
            }
            Card(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 15.dp)
                    .fillMaxHeight(.6f),
                shape = RoundedCornerShape(15.dp),
                colors = cardColors
            ) {
                Text(
                    text = "Add Post",
                    color = Color(0xFF134074),
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp)
                )
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Button(
                        modifier = Modifier.background(Color.Transparent),
                        onClick = {
                            check = true
                            galleryLauncher.launch("image/*") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.addicon),
                            contentDescription = "ImageButton",
                            modifier = Modifier
                                .size(48.dp)
                                .padding(8.dp),
                            alignment = Alignment.Center
                        )
                    }
                    imageUri?.let { uri ->
                        Image(
                            painter = rememberAsyncImagePainter(uri),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxSize()
                        )
                    }
                }
            }
            var userdes by remember { mutableStateOf("") }

            TextField(
                value = userdes,
                onValueChange = { userdes = it },
                placeholder = { Text("Enter description") },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Black,
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxHeight(.5f)
                    .fillMaxWidth(.9f)
                    .padding(10.dp, top = 2.dp),
                shape = RoundedCornerShape(5.dp),
            )

            Button(
                modifier = Modifier.background(Color.Transparent),
                onClick = {
                    if (userdes.isNotEmpty() && imageUri != null) {
                        GlobalScope.launch(Dispatchers.Main) {
                            val imageUrl = uploadImageToFirebase(context, imageUri!!, userdes, "posts")
                            imageUrl?.let { url ->
                                val post = Post(
                                    image = url,
                                    description = userdes,
                                    time = System.currentTimeMillis()
                                )

                                CoroutineScope(Dispatchers.IO).launch {
                                    val postReq = remoteRepo.createPost(post)
                                    createPostResult.value = postReq
                                }
                            }
                        }
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, "Please select an image and enter a description", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Post")
            }}

        createPostResult.value?.let { result ->
            if (result is Result.Success) {
                navController.navigate(AppScreens.ClubAdminMainScreen.route) {
                    popUpTo(AppScreens.ClubAdminMainScreen.route) {
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
        navController.navigate(route = ClubAdminBottomBarScreen.Home.route) {
            popUpTo(route = ClubAdminBottomBarScreen.Home.route) {
                inclusive = true
            }
        }
    }
}


