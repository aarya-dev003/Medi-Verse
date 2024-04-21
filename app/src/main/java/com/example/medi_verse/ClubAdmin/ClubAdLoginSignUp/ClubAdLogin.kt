package com.example.medi_verse.ClubAdmin.ClubAdLoginSignUp

import androidx.compose.foundation.Image
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
import com.example.medi_verse.repository.RemoteRepo
import com.example.requests.ClubLoginRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubAdLogin(AppnavController: NavController, remoteRepo: RemoteRepo) {
    Box(
        modifier = Modifier
            .fillMaxSize(),

        contentAlignment = Alignment.TopCenter,
    ) {
        Image(painter = painterResource(id = R.drawable.loginbackground), contentDescription = "Demo image", modifier = Modifier.matchParentSize(), contentScale = ContentScale.FillBounds)
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
            ){
                val useremailvalue= remember { mutableStateOf("") }
                val userpasswordvalue= remember { mutableStateOf("") }
                TextField(value =useremailvalue.value , onValueChange = { useremailvalue.value=it},label = { Text(text = "Enter email") },
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
                TextField(value =userpasswordvalue.value , onValueChange = { userpasswordvalue.value=it},label = { Text(text = "Enter password") },
                    modifier = Modifier
                        .padding(vertical = 18.dp),
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
                Button(onClick = { AppnavController.navigate(AppScreens.ClubAdminMainScreen.route)
                                 val club = ClubLoginRequest(
                                     username = useremailvalue.value,
                                     password = userpasswordvalue.value
                                 )
                    CoroutineScope(Dispatchers.IO).launch {
                        // Call the createUser method from the RemoteRepo
                        val result = remoteRepo.loginClub(club)
                        // Handle the result as needed
                    }

                                 },
                    modifier = Modifier.size(width = 150.dp, height = 50.dp),
                    colors= ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White,
                    ), ) {
                    Text(text = "Login")
                }
            }

        }
    }
}