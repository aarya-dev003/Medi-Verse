
package com.example.medi_verse.Student.StScreens

import android.annotation.SuppressLint
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
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.medi_verse.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(widthDp = 384, heightDp = 630)
@Composable
fun StAnnouncements() {
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
    list.add(AnnoucementsCustomDatatype(R.drawable.annoucementshumanpic,"Third-year college hours are from 10 to 5."))
    list.add(AnnoucementsCustomDatatype(R.drawable.annoucementshumanpic,"Exams for the current semester will commence from next Monday. Make sure you are well-prepared."))
    list.add(AnnoucementsCustomDatatype(R.drawable.annoucementshumanpic,"Library hours have been extended for the exam period. Take advantage of this opportunity to study in a quiet environment."))
    list.add(AnnoucementsCustomDatatype(R.drawable.annoucementshumanpic,"The annual cultural fest 'Verve' is scheduled for next month. Start preparing your performances and exhibits."))
    list.add(AnnoucementsCustomDatatype(R.drawable.annoucementshumanpic,"The college canteen will remain closed for renovation work starting next week. Alternative arrangements have been made for food services."))
    list.add(AnnoucementsCustomDatatype(R.drawable.annoucementshumanpic,"An industry expert seminar on 'Future Trends in Technology' will be held this Friday. Don't miss this opportunity to gain insights into the latest developments."))
    list.add(AnnoucementsCustomDatatype(R.drawable.annoucementshumanpic,"The college sports day is scheduled for next Saturday. Get ready to showcase your athletic talents and team spirit."))
    list.add(AnnoucementsCustomDatatype(R.drawable.annoucementshumanpic,"A blood donation camp will be organized on campus next week. Your participation can save lives."))
    list.add(AnnoucementsCustomDatatype(R.drawable.annoucementshumanpic,"The college is hosting a career fair next month. Network with industry professionals and explore job opportunities."))
    list.add(AnnoucementsCustomDatatype(R.drawable.annoucementshumanpic,"An alumni meet is planned for next weekend. Reconnect with your former classmates and mentors."))
    list.add(AnnoucementsCustomDatatype(R.drawable.annoucementshumanpic,"The college is organizing a tree plantation drive to promote environmental sustainability. Join us in making a positive impact."))

    return list
}
