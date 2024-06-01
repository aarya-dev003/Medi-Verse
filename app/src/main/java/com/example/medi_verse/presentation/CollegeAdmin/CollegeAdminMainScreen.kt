package com.example.medi_verse.presentation.CollegeAdmin

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
import com.example.medi_verse.presentation.CollegeAdmin.CollegeAdNav.CollegeAdBottomBarScreen
import com.example.medi_verse.presentation.CollegeAdmin.CollegeAdNav.CollegeAdBottomNavGraph
import com.example.medi_verse.repository.RemoteRepo
import com.example.medi_verse.utils.SessionManager


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CollegeAdminMainScreen(context:Context, remoteRepo: RemoteRepo, sessionManager: SessionManager, AppnavController:NavController) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { CollegeAdminBottomBar(navController = navController) }
    ){
        CollegeAdBottomNavGraph(CollegenavController = navController,context, remoteRepo,sessionManager, AppnavController)
    }
}

@Composable
fun CollegeAdminBottomBar(navController: NavHostController) {
    val screens = listOf(
        CollegeAdBottomBarScreen.Home,
        CollegeAdBottomBarScreen.Announcements,
        CollegeAdBottomBarScreen.RegisterClubAdmin
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
    screen: CollegeAdBottomBarScreen,
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