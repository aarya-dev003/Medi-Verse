package com.example.medi_verse.ClubAdmin.ClubAdScreens

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
import com.example.medi_verse.ClubAdmin.ClubAdNav.ClubAdminBottomBarScreen
import com.example.medi_verse.R
import com.example.medi_verse.ui.theme.BackgroundColor
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

private data class ImageWithDescription(val uri: Uri, val description: String)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubAdAddPosts (context: Context, navController: NavController) {
    Box(modifier = Modifier
        .background(BackgroundColor)
        .fillMaxSize(), contentAlignment = Alignment.TopCenter){
        var imageUriList by remember {
            mutableStateOf<List<Uri>>(emptyList())
        }
        val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetMultipleContents(),
            onResult = { uriList ->
                imageUriList = uriList
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
                    .padding(start = 20.dp, end = 20.dp, bottom = 15.dp).fillMaxHeight(.6f),
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
                    Row {
                        imageUriList.forEach { uri ->
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
                    .fillMaxWidth(.9f).padding(10.dp, top = 2.dp),
                shape = RoundedCornerShape(5.dp),
            )
            Button(
                modifier = Modifier.background(Color.Transparent),
                onClick = {
                    val imagesWithDescriptions = imageUriList.map { uri ->
                        ImageWithDescription(uri, userdes)
                    }
                    uploadImagesToFirebase(context, imagesWithDescriptions)
                    Toast.makeText(context, "Photo posted successfully", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Post")
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

private fun uploadImagesToFirebase(context: Context, imageList: List<ImageWithDescription>) {
    val storageRef = Firebase.storage.reference
    val imagesRef = storageRef.child("images")

    imageList.forEachIndexed { index, image ->
        val fileRef = imagesRef.child("image_$index.jpg")
        val uploadTask = fileRef.putFile(image.uri)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            Log.d(TAG, "Image uploaded successfully: ${taskSnapshot.metadata?.path}")
            val imageUrl = taskSnapshot.metadata?.path
            val imageDescription = image.description
            Toast.makeText(context, "Photo posted successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener { exception ->
            Log.e(TAG, "Error uploading image: $exception")
            Toast.makeText(context, "Failed to post photo: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
private const val TAG = "ClubAdAddPosts"


/**import android.content.Context
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
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val TAG = "ClubAdAddPosts"

private data class ImageWithDescription(val uri: Uri, val description: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubAdAddPosts (context: Context, navController: NavController) {
    Box(modifier = Modifier
        .background(BackgroundColor)
        .fillMaxSize(), contentAlignment = Alignment.TopCenter){
        var imageList by remember {
            mutableStateOf<List<ImageWithDescription>>(emptyList())
        }
        val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetMultipleContents(),
            onResult = { uriList ->
                // Here you can prompt the user to add descriptions for the selected images
                val descriptions = uriList.map { uri ->
                    // For now, let's assume default descriptions
                    ImageWithDescription(uri, "Default description")
                }
                imageList = descriptions
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
                    .padding(start = 20.dp, end = 20.dp, bottom = 15.dp).fillMaxHeight(.6f),
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
                    Row {
                        imageList.forEach { image ->
                            Image(
                                painter = rememberAsyncImagePainter(image.uri),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .fillMaxSize()
                            )
                        }
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
                    .fillMaxWidth(.9f).padding(10.dp, top = 2.dp),
                shape = RoundedCornerShape(5.dp),
            )
            Button(
                modifier = Modifier.background(Color.Transparent),
                onClick = {
                    uploadImagesToFirebase(context, imageList)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Post")
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

private fun uploadImagesToFirebase(context: Context, imageList: List<ImageWithDescription>) {
    val storageRef = Firebase.storage.reference
    val imagesRef = storageRef.child("images")

    imageList.forEachIndexed { index, image ->
        val fileRef = imagesRef.child("image_$index.jpg")
        val uploadTask = fileRef.putFile(image.uri)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            // File uploaded successfully
            Log.d(TAG, "Image uploaded successfully: ${taskSnapshot.metadata?.path}")
            // Now, you can also save the description to a database or associate it with the image in some way
            val imageUrl = taskSnapshot.metadata?.path
            val imageDescription = image.description
            // Here you can perform any further actions, like saving the image URL and description to a database
            // or updating UI accordingly.
            Toast.makeText(context, "Photo posted successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener { exception ->
            // Handle any errors
            Log.e(TAG, "Error uploading image: $exception")
            Toast.makeText(context, "Failed to post photo: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }
}


**/