package com.example.medi_verse.presentation.Student.StScreens

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.contentColorFor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.medi_verse.App.AppScreens
import com.example.medi_verse.presentation.Student.StNav.HomeBottomBarScreen
import com.example.medi_verse.data.remote.model.FeedbackRequest
import com.example.medi_verse.repository.RemoteRepo
import com.example.medi_verse.utils.Result
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StFeedback(navController: NavController, context: Context, remoteRepo: RemoteRepo) {
    val createPostResult = remember { mutableStateOf<Result<String>?>(null) }
    val pagerState = rememberPagerState()
    val pages = listOf("Campus","Clubs","Others")
    val defaultIndicator = @Composable { tabPositions: List<TabPosition> ->
        TabRowDefaults.Indicator(
            Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
        )
    }
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        CustomIndicator(tabPositions, pagerState)
    }
    Column {
            Text(
                text = "Feedback",
                color = Color(0xFF134074),
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
            )
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = indicator,
                backgroundColor = Color(0xFF8DA9C4),
                contentColor = contentColorFor(backgroundColor)

            ) {
                pages.forEachIndexed { index, title ->
                    Tab(
                        modifier = Modifier.zIndex(6f),
                        text = { Text(text = title) },
                        selected = pagerState.currentPage == index,
                        onClick = { /* TODO */ },
                    )
                }
            }
            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFEDF2FB)),
                count = pages.size,
                state = pagerState,
            ) { page ->
                Box(Modifier.fillMaxSize(),contentAlignment = Alignment.TopCenter) {
                    var issueT : String = "admin"
                    var textContent=""
                    if (page==0){
                        textContent="Campus related suggestion"
                        issueT = "admin"
                    }
                    if (page==1){
                        textContent="Club related suggestion"
                        issueT = "club"
                    }
                    if (page==2){
                        textContent="Other suggestion"
                        issueT = "admin"
                    }
                    Column {
                        val uservalue= remember { mutableStateOf("") }
                        TextField(
                            value = uservalue.value,
                            onValueChange = {
                                uservalue.value = it
                            },
                            label = { Text(text = textContent) },
                            colors = TextFieldDefaults.textFieldColors(cursorColor = Color.Black,
                                unfocusedLabelColor = Color.Black,
                                focusedLabelColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedTextColor = Color.Black,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                containerColor = Color.White,
                            ),
                            textStyle = TextStyle(color = Color.Black),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .padding(top = 140.dp)
                        )
                        Button(
                            onClick = {
                                if(page == 0)
                                    issueT = "admin"
                                else if(page == 1)
                                    issueT = "club"
                                else
                                    issueT = "admin"
                                val feedback = FeedbackRequest(
                                    issueType = issueT,
                                    issue = uservalue.value,
                                    time = System.currentTimeMillis()
                                )
                                CoroutineScope(Dispatchers.IO).launch {
                                    val feedbackReq = remoteRepo.createFeedback(feedback)
                                    createPostResult.value = feedbackReq
                                }
                            },
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .align(Alignment.CenterHorizontally),
                            colors=ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF13315C),
                                contentColor = Color.White,
                            ),
                        ) {
                            Text(text = "Send")
                        }
                    }

                    createPostResult.value?.let { result ->
                        if (result is Result.Success) {
                            Toast.makeText(context, "Feedback Sent", Toast.LENGTH_SHORT).show()
                        } else if (result is Result.Error) {
                            Toast.makeText(context, result.errorMessage.toString().trim(), Toast.LENGTH_SHORT).show()
                        } else {
//                            Toast.makeText(context, "Some Unexpected Error Occured", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        }


    BackHandler {
    navController.navigate(route = HomeBottomBarScreen.Home.route) {
        popUpTo(route = HomeBottomBarScreen.Home.route) {
            inclusive = true
        }
    }
    }
}
@OptIn(ExperimentalPagerApi::class)
@Composable
private fun CustomIndicator(tabPositions: List<TabPosition>, pagerState: PagerState) {
    val transition = updateTransition(pagerState.currentPage, label = "")
    val indicatorStart by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 50f)
            } else {
                spring(dampingRatio = 1f, stiffness = 1000f)
            }
        }, label = ""
    ) {
        tabPositions[it].left
    }

    val indicatorEnd by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 1000f)
            } else {
                spring(dampingRatio = 1f, stiffness = 50f)
            }
        }, label = ""
    ) {
        tabPositions[it].right
    }

    Box(
        Modifier
            .offset(x = indicatorStart)
            .wrapContentSize(align = Alignment.BottomStart)
            .width(indicatorEnd - indicatorStart)
            .padding(2.dp)
            .fillMaxSize()
            .background(color = Color.White, RoundedCornerShape(50))
            .zIndex(1f)
    )

}

