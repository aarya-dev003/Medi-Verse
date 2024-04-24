
package com.example.medi_verse.Student.StScreens

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medi_verse.R
import com.example.medi_verse.Student.StNav.HomeBottomBarScreen
import com.example.medi_verse.data.remote.model.Announcement
import com.example.medi_verse.data.remote.model.GetPost
import com.example.medi_verse.repository.RemoteRepo
import com.example.medi_verse.utils.Result

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StAnnouncements(navController: NavController, remoteRepo: RemoteRepo,context: Context) {
    val announcementResult = remember { mutableStateOf<Result<List<Announcement>>?>(null) }

    // Effect to trigger the API call when this composable is first shown
    LaunchedEffect(Unit) {
        try {
            // Call the function to retrieve posts
            val result = remoteRepo.getAnnouncementUser()

            // Update the state with the result of the API call
            announcementResult.value = result
        } catch (e: Exception) {
            // Update the state with the error if an exception occurs
            announcementResult.value = Result.Error(e.message ?: "An unexpected error occurred", emptyList())
        }
    }

    // UI code to display the result
    announcementResult.value?.let { result ->
        when (result) {
            is Result.Success -> {
                // Handle success case
                val announcement = result.data // Retrieve the list of posts
                if (announcement!!.isNotEmpty()) {
                    // If there are posts, display them
                    // You can navigate to the desired screen using navController if needed
                    // Example: navController.navigate(route = AppScreens.SomeScreen.route)
                    announcement.forEach {

                    }
                } else {
                    // If no posts are available, display a message
                    Toast.makeText(context, "No Announcement available", Toast.LENGTH_SHORT).show()
                }
            }
            is Result.Error -> {
                // Handle error case
                // Show an error message to the user
                Toast.makeText(context, result.errorMessage, Toast.LENGTH_SHORT).show()
            }
            else -> {
                // Handle unexpected case
                // Log the error for debugging purposes
                Log.e("StHome", "Unexpected result type: $result")
                Toast.makeText(context, "Some unexpected error occurred", Toast.LENGTH_SHORT).show()
            }
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFD7E3FC)))
    Column(modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Announcements",
            color = Color(0xFF134074),
            fontSize = 30.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
        )
        LazyColumn(Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp)) {
            items(AnnoucementsDataList()) { item ->
                AnnoucementLayout(item.img, item.annoucement)
            }
        }
    }
//    navController.navigate(route = HomeBottomBarScreen.Home.route) {
//        popUpTo(route = HomeBottomBarScreen.Home.route) {
//            inclusive = true
//        }
//    }
    BackHandler {
        navController.navigate(route = HomeBottomBarScreen.Home.route) {
            popUpTo(route = HomeBottomBarScreen.Home.route) {
                inclusive = true
            }
        }
    }
}
@Composable
fun AnnoucementLayout(img: Int, annoucement: String) {
    Card(
        elevation = 8.dp,
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            Modifier.background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = img),
                contentDescription = "",
                modifier = Modifier
                    .size(55.dp)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Black, CircleShape)
                    .weight(.2f)
            )
            Column(modifier = Modifier.weight(.5f)) {
                Text(
                    text = annoucement, fontWeight = FontWeight.W300, color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }

}
data class AnnoucementsCustomDatatype(val img:Int,val annoucement:String)
fun AnnoucementsDataList(): MutableList<AnnoucementsCustomDatatype> {
    val list = mutableListOf<AnnoucementsCustomDatatype>()
    list.add(AnnoucementsCustomDatatype(R.drawable.annoucementshumanpic,"Placement drive is going on"))
    list.add(AnnoucementsCustomDatatype(R.drawable.annoucementshumanpic,"75% attendance is mandatory "))
    list.add(AnnoucementsCustomDatatype(R.drawable.annoucementshumanpic,"MoonStone is postponed"))
    list.add(AnnoucementsCustomDatatype(R.drawable.annoucementshumanpic,"Students who have below 75% attendance will be detained"))
    list.add(AnnoucementsCustomDatatype(R.drawable.annoucementshumanpic,"Maintain the decorum of the college do not any illegal activity in campus"))
    list.add(AnnoucementsCustomDatatype(R.drawable.annoucementshumanpic,"Tomorrow will be holiday on account of republic day"))

    return list
}
