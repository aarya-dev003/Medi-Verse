package com.example.medi_verse.Student.StScreens

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.example.Formator.formatTimestamp
import com.example.medi_verse.App.AppScreens
import com.example.medi_verse.R
import com.example.medi_verse.Student.StNav.HomeBottomBarScreen
import com.example.medi_verse.data.remote.model.GetPost
import com.example.medi_verse.repository.RemoteRepo
import com.example.medi_verse.utils.Result

@Composable
fun StHome(context: Context, navController: NavController, remoteRepo: RemoteRepo) {
    val postResult = remember { mutableStateOf<Result<List<GetPost>>?>(null) }

    LaunchedEffect(Unit) {
        try {
            val result = remoteRepo.retrievePostUser()
            postResult.value = result
        } catch (e: Exception) {
            postResult.value = Result.Error(e.message ?: "An unexpected error occurred", emptyList())
        }
    }

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = androidx.compose.material3.DrawerValue.Closed)
    val items =
        listOf(
            DrawerItem(Icons.Default.Share, "Share"),
            DrawerItem(Icons.Default.Email, "Mail us"),
        )

    var selectedItem by remember { mutableStateOf<DrawerItem?>(null) }
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(280.dp)
                    .fillMaxHeight()
            ) {
                // Drawer content
            }
        },
        content = {
            postResult.value?.let { result ->
                when (result) {
                    is Result.Success -> {
                        val posts = result.data
                        if (posts != null) {
                            if (posts.isNotEmpty()) {
                                Column {

                                        DisplayPost(posts, onMenuIconClick = { scope.launch { drawerState.open() } })

                                }
                            } else {
                                Toast.makeText(context, "No posts available", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    is Result.Error -> {
                        Toast.makeText(context, result.errorMessage, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Log.e("StHome", "Unexpected result type: $result")
                        Toast.makeText(context, "Some unexpected error occurred", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    )
    BackHandler {
        System.exit(0)
    }
}
//    navController.navigate(route = HomeBottomBarScreen.Home.route) {
//        popUpTo(route = HomeBottomBarScreen.Home.route) {
//            inclusive = true
//        }
//    }





@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn( ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ScafoldContent(
    post: List<GetPost>,
    onMenuIconClick:()->Unit
) {

    Box(modifier = Modifier
        .background(Color(0xFFEDF2FB))
        .fillMaxSize()){
        Column(Modifier.fillMaxSize()) {
            Text(
                text = "Medi-verse",
                color = Color.Black,
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
            Scaffold(
                modifier = Modifier,
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = Color(0xFFEDF2FB),
                        ),
                        title = {
                            val uservalue= remember { mutableStateOf("") }
                            TextField(
                                value = uservalue.value,
                                onValueChange = {
                                    uservalue.value = it
                                },
                                label = { Text(text = "Search") },
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
                            )

                        },
                        navigationIcon = {
                            IconButton(onClick = onMenuIconClick) {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu Icon",tint = Color.Black)
                            }
                        },
                        actions = {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Default.Notifications, contentDescription = "Search Icon", tint = Color.Black)
                            }
                        }
                    )
                }

            )
            {
                val pagerState = rememberPagerState(pageCount = { post.size })
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 60.dp)
                        .background(Color(0xFFEDF2FB))
                ) { pageIndex ->
                    PostItem(post = post[pageIndex])
                }
            }
        }
    }}
@Composable
fun DisplayPost(post: List<GetPost>, onMenuIconClick: () -> Unit) {
    ScafoldContent(
        post = post,
        onMenuIconClick = onMenuIconClick
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeLayout(
    posts: List<GetPost>
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        posts.forEach { post ->
            PostItem(post = post)
        }
    }
}

@Composable
fun PostItem(post: GetPost) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = post.username,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(post.image).build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            text = formatTimestamp(timestamp = post.time),
            color = Color(0xFF134074),
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        )
        Text(
            text = post.description,
            color = Color(0xFF13315C),
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}
fun openGmailApp(context: Context){
    try {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="vnd.android.cursor.item/email"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("anshgaur.work@gmail.com"))
        context.startActivity(intent)
    }
    catch (e: ActivityNotFoundException){

    }
}
fun openWhatsApp(context: Context) {
    val appMsg:String="Hey check out our app"+"https://play.google.com/store/apps/details?id=com.example.medi_verse"
    val intent=Intent(Intent.ACTION_SEND)
    intent.putExtra((Intent.EXTRA_TEXT),appMsg)
    intent.type="text/plain"
    context.startActivity(intent)



}
data class DrawerItem(val icon: ImageVector, val label: String)




