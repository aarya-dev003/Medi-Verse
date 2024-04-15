package com.example.medi_verse.ClubAdmin.ClubAdScreens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medi_verse.R
import com.example.medi_verse.Student.StScreens.DrawerItem
import com.example.medi_verse.Student.StScreens.ScafoldContent
import com.example.medi_verse.Student.StScreens.openGmailApp
import com.example.medi_verse.Student.StScreens.openWhatsApp
import com.example.medi_verse.ui.theme.BackgroundColor
import kotlinx.coroutines.launch


@Composable
fun ClubAdHome(context: Context) {

    val scope= rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val items =
        listOf(
            DrawerItem(Icons.Default.Share, "Share"),
            DrawerItem(Icons.Default.Email, "Mail us"),
            //DrawerItem(Icons.Default.ExitToApp, "Logout")
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
                            Image(
                                painter = painterResource(id = R.drawable.boy),
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .size(150.dp)
                            )
                            var name = "Noah Walker"
                            val email="NoahWalker02@gmail.com"
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
                            else{
                                scope.launch {
                                    drawerState.close()
                                }
                                selectedItem=null
                            }
                        },
                        icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                    )
                }
            }
        },
        content = {
            ScafoldContent(
                onMenuIconClick = {scope.launch { drawerState.open() }}
            )
        }
    )
}