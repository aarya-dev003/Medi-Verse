package com.example.medi_verse.presentation.Student.StudentLoginSignup

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.medi_verse.data.remote.model.LoginRequest
import com.example.medi_verse.repository.RemoteRepo
import com.example.medi_verse.utils.Result
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StLogin(context: Context, AppnavController: NavController, remoteRepo: RemoteRepo) {
    val useremailvalue = remember { mutableStateOf("") }
    val userpasswordvalue = remember { mutableStateOf("") }
    var userpassvisiblity by remember { mutableStateOf(false) }
    val loginResult = remember { mutableStateOf<Result<String>?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$")

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Image(
            painter = painterResource(id = R.drawable.loginbackground),
            contentDescription = "Demo image",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.finaliconlogo),
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )

            Text(
                text = "Login",
                color = Color.Black,
                fontFamily = FontFamily.Monospace,
                fontSize = 30.sp,
            )

            Column(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val icon = if (userpassvisiblity) {
                    painterResource(id = com.google.android.material.R.drawable.design_ic_visibility)
                } else {
                    painterResource(id = com.google.android.material.R.drawable.design_ic_visibility_off)
                }

                TextField(
                    value = useremailvalue.value.lowercase(),
                    onValueChange = { newValue ->
                        useremailvalue.value = newValue
                    },
                    label = { Text(text = "Enter email") },
                    modifier = Modifier.padding(vertical = 18.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        unfocusedLabelColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        containerColor = Color.White,
                        errorLabelColor = Color.Red,

                        ),
                    textStyle = TextStyle(color = Color.Black),
                    shape = RoundedCornerShape(12.dp)
                )

                TextField(
                    value = userpasswordvalue.value,
                    onValueChange = {
                        userpasswordvalue.value = it
                    },
                    label = { Text(text = "Enter password") },
                    modifier = Modifier.padding(vertical = 18.dp),
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
                        if (useremailvalue.value.isEmpty() || !emailRegex.matches(useremailvalue.value)) {
                            Toast.makeText(context, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        if (userpasswordvalue.value.isEmpty()) {
                            Toast.makeText(context, "Please enter a valid password", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        val newUser = LoginRequest(
                            email = useremailvalue.value,
                            password = userpasswordvalue.value
                        )
                        coroutineScope.launch {
                            val result = remoteRepo.loginUser(newUser)
                            loginResult.value = result
                        }
                    },
                    modifier = Modifier.size(width = 150.dp, height = 50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White,
                    ),
                ) {
                    Text(text = "Login")
                }

                Row(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(text = "Don't have an Account yet?")
                    val clickableText = "Signup"
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
                            AppnavController.navigate(AppScreens.StSignUp.route)
                        }
                    )
                }
            }

            LaunchedEffect(loginResult.value) {
                loginResult.value?.let { result ->
                    when (result) {
                        is Result.Success -> {
                            AppnavController.navigate(AppScreens.HomeMainScreen.route)
                        }
                        is Result.Error -> {
                            Toast.makeText(context, result.errorMessage.toString().trim(), Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(context, "Some Unexpected Error Occurred", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}
