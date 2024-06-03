package com.example.medi_verse.presentation.Student.StScreens

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
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.medi_verse.presentation.Formator.formatTimestamp
import com.example.medi_verse.R
import com.example.medi_verse.presentation.Student.StNav.HomeBottomBarScreen
import com.example.medi_verse.data.remote.model.GetPost
import com.example.medi_verse.repository.RemoteRepo
import com.example.medi_verse.utils.Result
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.medi_verse.App.AppScreens
import com.example.medi_verse.utils.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Composable
fun StHome(context: Context, HomenavController: NavController, remoteRepo: RemoteRepo,sessionManager: SessionManager,AppnavController:NavController) {
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
            DrawerItem(Icons.Default.ExitToApp, "Logout")
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background((Color.White)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier
                        .background(Color.White)
                        .padding(top = 20.dp, bottom = 30.dp), contentAlignment = Alignment.BottomCenter) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                        ) {
                            Text(text = "Hey!", modifier = Modifier.padding(start = 10.dp), fontSize = 64.sp, fontWeight = FontWeight.W300
                            )
                            var name by remember { mutableStateOf("") }
                            var email by remember { mutableStateOf("") }
                            LaunchedEffect(Unit) {
                                name = sessionManager.getCurrentUsername() ?: ""
                                email = sessionManager.getCurrentEmail() ?: "$name+@gmail.com"
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = name,
                                color = Color(0xFF13315C),
                                fontSize = 20.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(start = 10.dp)
                            )
                            Text(
                                text = email,
                                color = Color(0xFF13315C),
                                fontSize = 20.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        }
                    }
                }
                items.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(text = item.label) },
                        selected = item == selectedItem,
                        onClick =
                        {
                            if (item.label=="Mail us"){
                                openGmailApp(context)
                                selectedItem=null
                            }
                            if (item.label=="Share"){
                                openWhatsApp(context)
                                selectedItem=null
                            }
                            if (item.label == "Logout") {
                                CoroutineScope(Dispatchers.IO).launch {
                                    sessionManager.logout()
                                }
                                AppnavController.navigate(AppScreens.Decision.route) {
                                    popUpTo(AppScreens.Decision.route) {
                                        inclusive = true
                                        AppnavController.popBackStack()
                                    }
                                }
                                selectedItem = null
                            }
                            else{
                                scope.launch {
                                    drawerState.close()
                                }
                                selectedItem=null
                            }
                        },
                        icon = { androidx.compose.material3.Icon(imageVector = item.icon, contentDescription = item.label) },
                    )
                }


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
                                    com.example.medi_verse.presentation.ClubAdmin.ClubAdScreens.DisplayPost(
                                        posts,
                                        onMenuIconClick = { scope.launch { drawerState.open() } },
                                        AppnavController
                                    )
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ScafoldContent(
    post: List<GetPost>,
    onMenuIconClick: () -> Unit,
    AppnavController: NavController

) {
    val pagerState = rememberPagerState(pageCount = { post.size })
    var searchText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .background(Color(0xFFEDF2FB))
            .fillMaxSize()
    ) {
        Column(Modifier.fillMaxSize()
        ) {

            Scaffold(
                modifier = Modifier,
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = Color(0xFFEDF2FB),
                        ),
                        title = {
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
//                            TextField(
//                                modifier = Modifier.fillMaxWidth(),
//                                value = searchText,
//                                onValueChange = { searchText = it },
//                                label = { Text("Search Video Title") },
//                                trailingIcon = {
//                                    IconButton(
//                                        onClick = {
//                                            Log.d("searchText", searchText)
//                                            AppnavController.navigate("${AppScreens.SearchResults.route}/$searchText") {
//                                                launchSingleTop = true
//                                                popUpTo(AppScreens.SearchResults.route) {
//                                                    inclusive = true
//                                                }
//                                            }
//
//                                        },
//                                    ) {
//                                        Icon(Icons.Filled.Search, contentDescription = "Search")
//                                    }
//                                },
//                                colors = TextFieldDefaults.textFieldColors(
//                                    cursorColor = Color.Black,
//                                    unfocusedLabelColor = Color.Black,
//                                    focusedLabelColor = Color.Black,
//                                    unfocusedTextColor = Color.Black,
//                                    focusedTextColor = Color.Black,
//                                    unfocusedIndicatorColor = Color.Transparent,
//                                    focusedIndicatorColor = Color.Transparent,
//                                    containerColor = Color.White,
//                                ),
//                                textStyle = TextStyle(color = Color.Black),
//                                shape = RoundedCornerShape(12.dp),
//                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = onMenuIconClick) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu Icon",
                                    tint = Color.Black
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Search Icon",
                                    tint = Color.Black
                                )
                            }
                        }
                    )
                }
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFEDF2FB))
                ) { pageIndex ->
                    Spacer(modifier = Modifier.height(10.dp))
                    PostItem(post = post[pageIndex])
                }
            }
        }
    }
}

@Composable
fun DisplayPost(post: List<GetPost>, onMenuIconClick: () -> Unit, AppnavController: NavController) {
    ScafoldContent(
        post = post,
        onMenuIconClick = onMenuIconClick,
        AppnavController
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
            .padding(horizontal = 15.dp)
            .padding(top = 0.dp)
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .clip(RoundedCornerShape(16.dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(post.image)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = post.username,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp,
                fontFamily = FontFamily.Serif,
                color = Color.Black,
                modifier = Modifier.padding(start = 0.dp)
            )
            Text(
                text = formatTimestamp(timestamp = post.time),
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp,
                fontFamily = FontFamily.Serif,
                color = Color.Black,
                modifier = Modifier.padding(end = 0.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = post.description,
            color = Color(0xFF13315C),
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(start = 0.dp, bottom = 7.dp)
        )
    }
}


fun openGmailApp(context: Context) {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "vnd.android.cursor.item/email"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("anshgaur.work@gmail.com"))
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {

    }
}

fun openWhatsApp(context: Context) {
    val appMsg = "Hey check out our app" + "https://play.google.com/store/apps/details?id=com.example.medi_verse"
    val intent = Intent(Intent.ACTION_SEND)
    intent.putExtra(Intent.EXTRA_TEXT, appMsg)
    intent.type = "text/plain"
    context.startActivity(intent)
}

data class DrawerItem(val icon: ImageVector, val label: String)