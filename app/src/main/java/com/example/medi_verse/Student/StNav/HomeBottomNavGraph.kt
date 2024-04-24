package com.example.medi_verse.Student.StNav

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.medi_verse.Student.StScreens.StAnnouncements

import com.example.medi_verse.Student.StScreens.StFeedback
import com.example.medi_verse.Student.StScreens.StHome
import com.example.medi_verse.repository.RemoteRepo

@Composable
fun HomeBottomNavGraph(HomenavController: NavHostController, context: Context, remoteRepo: RemoteRepo) {
    NavHost(navController = HomenavController,
        startDestination = HomeBottomBarScreen.Home.route ){
        composable(route = HomeBottomBarScreen.Home.route){
            StHome(context,HomenavController, remoteRepo)
        }
        composable(route = HomeBottomBarScreen.Announcements.route){
            StAnnouncements(HomenavController, remoteRepo, context)

        }
        composable(route = HomeBottomBarScreen.Feedback.route){
            StFeedback(HomenavController)
        }
        composable(route = HomeBottomBarScreen.StLogin.route){

        }
    }
}