package com.example.medi_verse.Student.StudentLoginSignup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medi_verse.App.AppScreens
import com.example.medi_verse.R
import com.example.medi_verse.data.remote.model.RegisterRequest
import com.example.medi_verse.repository.RemoteRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StSignup(AppnavController: NavController, remoteRepo: RemoteRepo) {
    Box(
        modifier = Modifier
            .fillMaxSize(),

        contentAlignment = Alignment.TopCenter,
    ){
        Image(painter = painterResource(id = R.drawable.loginbackground), contentDescription = "Demo image", modifier = Modifier.matchParentSize(), contentScale = ContentScale.FillBounds)
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 50.dp)
        ){
            Text(
                text = "SignUp",
                color = Color.Black,
                fontFamily = FontFamily.Monospace,
                fontSize = 30.sp,
                modifier = Modifier.padding(top = 30.dp)
            )
            val newusernamevalue= remember { mutableStateOf("") }
            val newuserusernamevalue= remember { mutableStateOf("") }
            val newuseremailvalue= remember { mutableStateOf("") }
            val newuserpasswordvalue= remember { mutableStateOf("") }
            TextField(value =newusernamevalue.value , onValueChange = { newusernamevalue.value=it},label = { Text(text = "Enter name")},
                modifier = Modifier
                    .padding(vertical = 18.dp),
//                        trailingIcon = {
//                                       Icon(
//                                           painter = painterResource(id = R.drawable.lockiconlogin) ,
//                                           contentDescription = "lock",
//                                       )
//                        },

                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor =Color.Black,
                    unfocusedTextColor =Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    containerColor = Color.White,
                ),
                textStyle = TextStyle(color = Color.Black),
                shape = RoundedCornerShape(12.dp)
            )
            TextField(value =newuserusernamevalue.value , onValueChange = { newuserusernamevalue.value=it},label = { Text(text = "Enter user name")},
                modifier = Modifier
                    .padding(vertical = 18.dp),
//                        trailingIcon = {
//                                       Icon(
//                                           painter = painterResource(id = R.drawable.lockiconlogin) ,
//                                           contentDescription = "lock",
//                                       )
//                        },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor =Color.Black,
                    unfocusedTextColor =Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    containerColor = Color.White,
                ),
                textStyle = TextStyle(color = Color.Black),
                shape = RoundedCornerShape(12.dp)
            )
            TextField(value =newuseremailvalue.value , onValueChange = { newuseremailvalue.value=it},label = { Text(text = "Enter email")},
                modifier = Modifier
                    .padding(vertical = 18.dp),
//                        trailingIcon = {
//                                       Icon(
//                                           painter = painterResource(id = R.drawable.lockiconlogin) ,
//                                           contentDescription = "lock",
//                                       )
//                        },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor =Color.Black,
                    unfocusedTextColor =Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    containerColor = Color.White,
                ),
                textStyle = TextStyle(color = Color.Black),
                shape = RoundedCornerShape(12.dp)
            )
            TextField(value =newuserpasswordvalue.value , onValueChange = { newuserpasswordvalue.value=it},label = { Text(text = "Enter password")},
                modifier = Modifier
                    .padding(vertical = 18.dp),
//                        trailingIcon = {
//                                       Icon(
//                                           painter = painterResource(id = R.drawable.lockiconlogin) ,
//                                           contentDescription = "lock",
//                                       )
//                        },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor =Color.Black,
                    unfocusedTextColor =Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    containerColor = Color.White,
                ),
                textStyle = TextStyle(color = Color.Black),
                shape = RoundedCornerShape(12.dp)
            )
            Button(onClick = { AppnavController.navigate(AppScreens.HomeMainScreen.route)
                val newUser = RegisterRequest(
                    name = newusernamevalue.value,
                    username = newuserusernamevalue.value,
                    password = newuserpasswordvalue.value,
                    email = newuseremailvalue.value
                )

                // Launch a coroutine scope
                CoroutineScope(Dispatchers.IO).launch {
                    // Call the createUser method from the RemoteRepo
                    val result = remoteRepo.createUser(newUser)
                    // Handle the result as needed
                }
                             },
                modifier = Modifier.size(width = 150.dp, height = 50.dp),
                colors= ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                ), ) {
                Text(text = "Next")
            }
            Row (
                modifier = Modifier.padding(vertical = 8.dp)
            ){
                Text(text = "Already have an account?")
                val clickableText = "Login"
                ClickableText(
                    text = AnnotatedString.Builder(clickableText)
                        .apply {
                            addStyle(
                                style = SpanStyle(
                                    color = Color.Black,
                                    textDecoration = TextDecoration.Underline
                                ),
                                start = 0,
                                end = clickableText.length
                            )
                        }
                        .toAnnotatedString(),
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    onClick = {
                        AppnavController.navigate(AppScreens.StLogin.route)
                    }
                )
            }
        }

    }
}