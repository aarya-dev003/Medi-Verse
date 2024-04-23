package com.example.medi_verse.ClubAdmin.ClubAdNav

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.medi_verse.ClubAdmin.ClubAdNav.ClubAdminBottomBarScreen
import com.example.medi_verse.ClubAdmin.ClubAdScreens.ClubAdAddPosts
import com.example.medi_verse.ClubAdmin.ClubAdScreens.ClubAdFeedback
import com.example.medi_verse.ClubAdmin.ClubAdScreens.ClubAdHome
import com.example.medi_verse.repository.RemoteRepo

@Composable
fun ClubAdminBottomNavGraph(ClubnavController:NavHostController,context: Context, remoteRepo: RemoteRepo) {
    NavHost(navController = ClubnavController, startDestination = ClubAdminBottomBarScreen.Home.route ){
        composable(route= ClubAdminBottomBarScreen.Home.route){
            ClubAdHome(context = context,ClubnavController)
        }
        composable(route= ClubAdminBottomBarScreen.AddPost.route){
            ClubAdAddPosts(context,ClubnavController, remoteRepo)
        }
        composable(route= ClubAdminBottomBarScreen.Feedback.route){
            ClubAdFeedback(ClubnavController)
        }
    }
}