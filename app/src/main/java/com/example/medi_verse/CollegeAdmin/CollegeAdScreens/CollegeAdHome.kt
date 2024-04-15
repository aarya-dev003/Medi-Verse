package com.example.medi_verse.CollegeAdmin.CollegeAdScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medi_verse.ui.theme.BackgroundColor

@Preview
@Composable
fun CollegeAdHome() {
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
            val items: List<FeedbackCustomDatatype> = FeedbackDatalist()
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.fillMaxSize().padding(bottom = 36.dp),
                contentPadding = PaddingValues(16.dp),
                verticalItemSpacing = 16.dp,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                this.items(items) { item ->
                    FeedbackLayout(item = item)
                }
            }
        }
    }
}
@Composable
fun FeedbackLayout(item: FeedbackCustomDatatype) {
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
                    text = item.feedback, fontWeight = FontWeight.W300, color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

data class FeedbackCustomDatatype (val feedback:String)
fun FeedbackDatalist(): MutableList<FeedbackCustomDatatype> {
    val list = mutableListOf<FeedbackCustomDatatype>()
    list.add(FeedbackCustomDatatype("Assess member participation and brainstorm interactive events to boost engagement."))
    list.add(FeedbackCustomDatatype("Ensure opportunities for learning new tech skills through tutorials or study groups."))
    list.add(FeedbackCustomDatatype("Encourage teamwork on projects and coding challenges."))
    list.add(FeedbackCustomDatatype("Facilitate connections with industry pros through guest speakers or career panels"))
    list.add(FeedbackCustomDatatype("Establish a system for gathering member input and making improvements"))
    list.add(FeedbackCustomDatatype("Plan for the club's long-term success and continuity"))
    list.add(FeedbackCustomDatatype("Encourage participation in external events and conferences."))
    list.add(FeedbackCustomDatatype("Assess member participation and brainstorm interactive events to boost engagement."))
    list.add(FeedbackCustomDatatype("Ensure opportunities for learning new tech skills through tutorials or study groups."))
    list.add(FeedbackCustomDatatype("Encourage teamwork on projects and coding challenges."))
    list.add(FeedbackCustomDatatype("Facilitate connections with industry pros through guest speakers or career panels"))
    list.add(FeedbackCustomDatatype("Establish a system for gathering member input and making improvements"))
    list.add(FeedbackCustomDatatype("Plan for the club's long-term success and continuity"))
    list.add(FeedbackCustomDatatype("Encourage participation in external events and conferences."))
    list.add(FeedbackCustomDatatype("Assess member participation and brainstorm interactive events to boost engagement."))
    list.add(FeedbackCustomDatatype("Ensure opportunities for learning new tech skills through tutorials or study groups."))
    list.add(FeedbackCustomDatatype("Encourage teamwork on projects and coding challenges."))
    list.add(FeedbackCustomDatatype("Facilitate connections with industry pros through guest speakers or career panels"))
    list.add(FeedbackCustomDatatype("Establish a system for gathering member input and making improvements"))
    list.add(FeedbackCustomDatatype("Plan for the club's long-term success and continuity"))
    list.add(FeedbackCustomDatatype("Encourage participation in external events and conferences."))
    return list
}