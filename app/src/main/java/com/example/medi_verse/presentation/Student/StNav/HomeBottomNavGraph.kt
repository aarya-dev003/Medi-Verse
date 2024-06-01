package com.example.medi_verse.presentation.Student.StNav

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.medi_verse.presentation.Student.StScreens.StAnnouncements

import com.example.medi_verse.presentation.Student.StScreens.StFeedback
import com.example.medi_verse.presentation.Student.StScreens.StHome
import com.example.medi_verse.repository.RemoteRepo
import com.example.medi_verse.utils.SessionManager

@Composable
fun HomeBottomNavGraph(HomenavController: NavHostController, context: Context, remoteRepo: RemoteRepo, sessionManager: SessionManager, AppnavController:NavController) {
    NavHost(navController = HomenavController,
        startDestination = HomeBottomBarScreen.Home.route ){
        composable(route = HomeBottomBarScreen.Home.route){
            StHome(context,HomenavController, remoteRepo,sessionManager,AppnavController)
        }
        composable(route = HomeBottomBarScreen.Announcements.route){
            StAnnouncements(HomenavController, remoteRepo, context)

        }
        composable(route = HomeBottomBarScreen.Feedback.route){
            StFeedback(HomenavController, context, remoteRepo)
        }
        composable(route = HomeBottomBarScreen.StLogin.route){

        }
    }
}