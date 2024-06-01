package com.example.medi_verse.presentation.Student.StudentLoginSignup

import android.content.Context
import android.widget.Toast
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medi_verse.App.AppScreens
import com.example.medi_verse.R
import com.example.medi_verse.data.remote.model.RegisterRequest
import com.example.medi_verse.repository.RemoteRepo
import com.example.medi_verse.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StSignup(context : Context, AppnavController: NavController, remoteRepo: RemoteRepo) {
    val loginResult = remember { mutableStateOf<Result<String>?>(null) }
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
            var userpasswordvalue = remember { mutableStateOf("") }
            var userpassvisiblity by remember { mutableStateOf(false) }
            val icon = if (userpassvisiblity) {
                painterResource(id = com.google.android.material.R.drawable.design_ic_visibility)
            } else {
                painterResource(id = com.google.android.material.R.drawable.design_ic_visibility_off)
            }
            TextField(value =newusernamevalue.value , onValueChange = { newusernamevalue.value=it},label = { Text(text = "Enter name")},
                modifier = Modifier
                    .padding(vertical = 18.dp),


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
                trailingIcon = {
                    IconButton(onClick = {
                        userpassvisiblity = !userpassvisiblity
                    }) {
                        Icon(
                            painter = icon,
                            contentDescription = "visibility toggle",
                            modifier = Modifier.padding(1.dp)
                        )
                    }
                },
                visualTransformation = if (userpassvisiblity) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
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
            Button(onClick = {
                val newUser = RegisterRequest(
                    name = newusernamevalue.value,
                    username = newuserusernamevalue.value,
                    password = newuserpasswordvalue.value,
                    email = newuseremailvalue.value
                )

                CoroutineScope(Dispatchers.IO).launch {
                    val result = remoteRepo.createUser(newUser)
                    loginResult.value = result
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

        loginResult.value?.let { result ->
            if (result is Result.Success) {
                AppnavController.navigate(AppScreens.HomeMainScreen.route)
            }else if(result is Result.Error){
                Toast.makeText(context, result.errorMessage.toString().trim(), Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "Some Unexpected Error Occured", Toast.LENGTH_SHORT).show()
            }
        }

    }
}