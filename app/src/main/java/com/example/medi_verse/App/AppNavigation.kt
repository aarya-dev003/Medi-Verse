package com.example.medi_verse.App

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medi_verse.presentation.ClubAdmin.ClubAdLoginSignUp.ClubAdLogin
import com.example.medi_verse.presentation.ClubAdmin.ClubAdminMainScreen
import com.example.medi_verse.presentation.CollegeAdmin.CollegeAdminMainScreen
import com.example.medi_verse.presentation.CollegeAdmin.LoginSignUp.CollegeAdLogin
import com.example.medi_verse.presentation.Student.HomeMainScreen
import com.example.medi_verse.presentation.Student.StudentLoginSignup.StLogin
import com.example.medi_verse.presentation.Student.StudentLoginSignup.StSignup
import com.example.medi_verse.repository.RemoteRepo
import com.example.medi_verse.utils.SessionManager

@Composable
fun AppNavigation(context: Context, remoteRepo: RemoteRepo, sessionManager: SessionManager) {
    val navController= rememberNavController()
    NavHost(navController =navController , startDestination = AppScreens.SplashScreen.route ){
        composable(route= AppScreens.SplashScreen.route){
            SplashScreen(navController, remoteRepo, sessionManager)
        }
        composable(route= AppScreens.FirstPage.route){
            FirstPage(AppnavController=navController)
        }
        composable(route= AppScreens.Decision.route){
            DecisionPage(AppnavController=navController)
        }
        composable(route= AppScreens.StLogin.route){
            StLogin(context ,AppnavController = navController, remoteRepo)
        }
        composable(route= AppScreens.HomeMainScreen.route){
            HomeMainScreen(context = context,remoteRepo,sessionManager,navController)
        }
        composable(route= AppScreens.StSignUp.route){
            StSignup(context, AppnavController = navController, remoteRepo)
        }
        composable(route= AppScreens.ClubAdLogin.route){
            ClubAdLogin(context,AppnavController = navController, remoteRepo)
        }
        composable(route= AppScreens.ClubAdminMainScreen.route){
            ClubAdminMainScreen(context, remoteRepo,navController,sessionManager)
        }
        composable(route= AppScreens.CollegeAdminMainScreen.route){
            CollegeAdminMainScreen(context,remoteRepo)
        }
        composable(route= AppScreens.CollegeAdLogin.route){
            CollegeAdLogin(context,AppnavController = navController, remoteRepo)
        }
        composable(route= AppScreens.CollegeAdSignup.route){
        }
    }
}
