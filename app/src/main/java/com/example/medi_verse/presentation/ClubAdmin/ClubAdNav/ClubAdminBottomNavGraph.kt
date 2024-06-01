package com.example.medi_verse.presentation.ClubAdmin.ClubAdNav

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.medi_verse.presentation.ClubAdmin.ClubAdNav.ClubAdminBottomBarScreen
import com.example.medi_verse.presentation.ClubAdmin.ClubAdScreens.ClubAdAddPosts
import com.example.medi_verse.presentation.ClubAdmin.ClubAdScreens.ClubAdFeedback
import com.example.medi_verse.presentation.ClubAdmin.ClubAdScreens.ClubAdHome
import com.example.medi_verse.repository.RemoteRepo
import com.example.medi_verse.utils.SessionManager

@Composable
fun ClubAdminBottomNavGraph(ClubnavController:NavHostController,context: Context, remoteRepo: RemoteRepo,AppnavController: NavController,sessionManager: SessionManager) {
    NavHost(navController = ClubnavController, startDestination = ClubAdminBottomBarScreen.Home.route ){
        composable(route= ClubAdminBottomBarScreen.Home.route){
            ClubAdHome(context = context,ClubnavController,remoteRepo,AppnavController,sessionManager)
        }
        composable(route= ClubAdminBottomBarScreen.AddPost.route){
            ClubAdAddPosts(context,ClubnavController, remoteRepo)
        }
        composable(route= ClubAdminBottomBarScreen.Feedback.route){
            ClubAdFeedback(ClubnavController, context , remoteRepo)
        }
        composable(route= ClubAdminBottomBarScreen.Feedback.route){
            ClubAdFeedback(ClubnavController, context , remoteRepo)
        }
    }
}