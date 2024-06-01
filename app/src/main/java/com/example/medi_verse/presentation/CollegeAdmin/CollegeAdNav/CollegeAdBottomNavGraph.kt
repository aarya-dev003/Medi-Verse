package com.example.medi_verse.presentation.CollegeAdmin.CollegeAdNav

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.medi_verse.presentation.CollegeAdmin.CollegeAdNav.CollegeAdBottomBarScreen
import com.example.medi_verse.presentation.CollegeAdmin.CollegeAdScreens.CollegeAdAnnoucements
import com.example.medi_verse.presentation.CollegeAdmin.CollegeAdScreens.CollegeAdHome
import com.example.medi_verse.presentation.CollegeAdmin.CollegeAdScreens.RegisterCollegeAdmin
import com.example.medi_verse.repository.RemoteRepo
import com.example.medi_verse.utils.SessionManager

@Composable
fun CollegeAdBottomNavGraph (CollegenavController: NavHostController, context: Context, remoteRepo: RemoteRepo, sessionManager: SessionManager, AppnavController: NavController){
    NavHost(navController = CollegenavController, startDestination = CollegeAdBottomBarScreen.Home.route ){
        composable(route= CollegeAdBottomBarScreen.Home.route){
            CollegeAdHome(CollegenavController, context, remoteRepo)
        }
        composable(route= CollegeAdBottomBarScreen.Announcements.route){
            CollegeAdAnnoucements(context = context,CollegenavController, remoteRepo )
        }
        composable(route= CollegeAdBottomBarScreen.RegisterClubAdmin.route){
           RegisterCollegeAdmin(navController = CollegenavController,remoteRepo =remoteRepo, context = context, sessionManager, AppnavController )
        }
    }
}