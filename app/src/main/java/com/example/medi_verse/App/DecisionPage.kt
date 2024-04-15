package com.example.medi_verse.App

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medi_verse.R


@Composable
fun DecisionPage(AppnavController: NavController) {
Box(modifier = Modifier
    .background(Color.White)
    .fillMaxSize(),
    contentAlignment = Alignment.TopCenter
)
    {
    Image(painter = painterResource(id = R.drawable.loginbackground), contentDescription = "Demo image", modifier = Modifier.matchParentSize(), contentScale = ContentScale.FillBounds)
        Text(text = "Tell us some about yourself", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 22.sp, modifier = Modifier.padding(top = 25.dp) )

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 170.dp),

    ){
        Text(text = "Are you an?", fontFamily = FontFamily.Monospace, fontSize = 25.sp,fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 25.dp) )

            Button(onClick = { AppnavController.navigate(AppScreens.StLogin.route)},
                modifier = Modifier.size(width = 150.dp, height = 65.dp).padding(top=20.dp),
                colors= ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                ), ) {
                Text(text = "Student")
            }
            Button(onClick = {AppnavController.navigate(AppScreens.ClubAdLogin.route)},
                modifier = Modifier.size(width = 150.dp, height = 65.dp).padding(top=20.dp),
                colors= ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                ), ) {
                Text(text = "Club Admin")
            }
            Button(onClick = {  AppnavController.navigate(AppScreens.CollegeAdLogin.route)},
                modifier = Modifier.size(width = 150.dp, height = 65.dp).padding(top=20.dp),
                colors= ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                ), ) {
                Text(text = "College Admin")
            }
    }
}
}