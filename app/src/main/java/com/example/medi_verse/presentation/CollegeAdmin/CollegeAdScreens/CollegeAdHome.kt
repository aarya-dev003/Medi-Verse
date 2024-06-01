package com.example.medi_verse.presentation.CollegeAdmin.CollegeAdScreens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.medi_verse.data.remote.model.FeedbackItem
import com.example.medi_verse.presentation.Formator.formatTimestamp
import com.example.medi_verse.repository.RemoteRepo
import com.example.medi_verse.ui.theme.BackgroundColor
import com.example.medi_verse.utils.Result

@Composable
fun CollegeAdHome(navController: NavController, context: Context, remoteRepo: RemoteRepo) {
    val feedbackResult = remember { mutableStateOf<Result<List<FeedbackItem>>?>(null) }

    // Effect to trigger the API call when this composable is first shown
    LaunchedEffect(Unit) {
        try {
            // Call the function to retrieve feedback
            val result = remoteRepo.getFeedbackAdmin()

            // Update the state with the result of the API call
            feedbackResult.value = result
        } catch (e: Exception) {
            // Update the state with the error if an exception occurs
            feedbackResult.value = Result.Error(e.message ?: "An unexpected error occurred", emptyList())
        }
    }

    // UI code to display the result
    feedbackResult.value?.let { result ->
        when (result) {
            is Result.Success -> {
                // Handle success case
                val feedback = result.data // Retrieve the list of feedback items
                if (feedback != null) {
                    if (feedback.isNotEmpty()) {
                        // If there are feedback items, display them
                        // You can navigate to the desired screen using navController if needed
                        // Example: navController.navigate(route = AppScreens.SomeScreen.route)
                        Box(modifier = Modifier
                            .background(BackgroundColor)
                            .fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(Modifier.fillMaxSize()) {
                                Text(
                                    text = "Students FeedBacks",
                                    color = Color(0xFF134074),
                                    fontSize = 30.sp,
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 15.dp)
                                )
                                LazyVerticalStaggeredGrid(
                                    columns = StaggeredGridCells.Fixed(2),
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(bottom = 36.dp),
                                    contentPadding = PaddingValues(16.dp),
                                    verticalItemSpacing = 16.dp,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    items(feedback) { item ->
                                        FeedbackLayout(item = item)
                                    }
                                }
                            }
                        }
                    } else {
                        // If no feedback items are available, display a message
                        Toast.makeText(context, "No Feedback available", Toast.LENGTH_SHORT).show()
                    }
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
}

@Composable
fun FeedbackLayout(item: FeedbackItem) {
    Card(
        elevation = 8.dp,
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            Modifier.background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(.5f)) {
                Text(
                    text = item.issue,
                    fontWeight = FontWeight.W300,
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(10.dp)
                )
                Text(
                    text = formatTimestamp(timestamp = item.time),
                    fontWeight = FontWeight.W300,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(start = 90.dp,bottom = 10.dp, end = 5.dp)
                )
            }
        }
    }
}
