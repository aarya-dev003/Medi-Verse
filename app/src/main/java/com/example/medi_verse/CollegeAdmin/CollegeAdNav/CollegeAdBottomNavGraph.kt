package com.example.medi_verse.CollegeAdmin.CollegeAdNav

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.medi_verse.CollegeAdmin.CollegeAdNav.CollegeAdBottomBarScreen
import com.example.medi_verse.CollegeAdmin.CollegeAdScreens.CollegeAdAnnoucements
import com.example.medi_verse.CollegeAdmin.CollegeAdScreens.CollegeAdHome
import com.example.medi_verse.CollegeAdmin.CollegeAdScreens.RegisterCollegeAdmin
import com.example.medi_verse.repository.RemoteRepo

@Composable
fun CollegeAdBottomNavGraph (CollegenavController: NavHostController, context: Context, remoteRepo: RemoteRepo){
    NavHost(navController = CollegenavController, startDestination = CollegeAdBottomBarScreen.Home.route ){
        composable(route= CollegeAdBottomBarScreen.Home.route){
            CollegeAdHome(CollegenavController, context, remoteRepo)
        }
        composable(route= CollegeAdBottomBarScreen.Announcements.route){
            CollegeAdAnnoucements(context = context,CollegenavController, remoteRepo )
        }
        composable(route= CollegeAdBottomBarScreen.RegisterClubAdmin.route){
           RegisterCollegeAdmin(navController = CollegenavController, context = context, remoteRepo =remoteRepo)
        }
    }

}