package com.example.medi_verse.presentation.ClubAdmin

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.medi_verse.presentation.ClubAdmin.ClubAdNav.ClubAdminBottomBarScreen
import com.example.medi_verse.presentation.ClubAdmin.ClubAdNav.ClubAdminBottomNavGraph
import com.example.medi_verse.repository.RemoteRepo
import com.example.medi_verse.utils.SessionManager


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ClubAdminMainScreen(context: Context, remoteRepo: RemoteRepo,AppnavController:NavController,sessionManager: SessionManager) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { ClubAdminBottomBar(navController = navController) }
    ){
        ClubAdminBottomNavGraph(ClubnavController = navController,context, remoteRepo, AppnavController = AppnavController,sessionManager )
    }
}
@Composable
fun ClubAdminBottomBar(navController: NavHostController) {
    val screens = listOf(
      ClubAdminBottomBarScreen.Home,
      ClubAdminBottomBarScreen.AddPost,
      ClubAdminBottomBarScreen.Feedback
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        backgroundColor = Color.White
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: ClubAdminBottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavController
) {
    BottomNavigationItem(
        label = { Text(text = screen.title) },
        icon = {
            Icon(
                painter = painterResource(id = screen.drawableId),
                contentDescription = "Navigation Icon",
                tint = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) Color.Black else Color.LightGray,
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route)
        }
    )
}