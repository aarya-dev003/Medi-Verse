package com.example.medi_verse.App

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medi_verse.ClubAdmin.ClubAdLoginSignUp.ClubAdLogin
import com.example.medi_verse.ClubAdmin.ClubAdLoginSignUp.ClubAdSignup
import com.example.medi_verse.ClubAdmin.ClubAdminMainScreen
import com.example.medi_verse.CollegeAdmin.CollegeAdminMainScreen
import com.example.medi_verse.CollegeAdmin.LoginSignUp.CollegeAdLogin
import com.example.medi_verse.CollegeAdmin.LoginSignUp.CollegeAdSignup
import com.example.medi_verse.Student.HomeMainScreen
import com.example.medi_verse.Student.StudentLoginSignup.StLogin
import com.example.medi_verse.Student.StudentLoginSignup.StSignup
import com.example.medi_verse.repository.RemoteRepo

@Composable
fun AppNavigation(context: Context, remoteRepo: RemoteRepo) {
    val navController= rememberNavController()
    NavHost(navController =navController , startDestination = AppScreens.FirstPage.route ){
        composable(route= AppScreens.FirstPage.route){
            FirstPage(AppnavController=navController)
        }
        composable(route= AppScreens.Decision.route){
            DecisionPage(AppnavController=navController)
        }
        composable(route= AppScreens.StLogin.route){
            StLogin(AppnavController = navController, remoteRepo)
        }
        composable(route= AppScreens.HomeMainScreen.route){
            HomeMainScreen(context = context)
        }
        composable(route= AppScreens.StSignUp.route){
            StSignup(AppnavController = navController, remoteRepo)
        }
        composable(route= AppScreens.ClubAdSignUp.route){
            ClubAdSignup(AppnavController = navController)
        }
        composable(route= AppScreens.ClubAdLogin.route){
            ClubAdLogin(AppnavController = navController, remoteRepo)
        }
        composable(route= AppScreens.ClubAdminMainScreen.route){
            ClubAdminMainScreen(context)
        }
        composable(route= AppScreens.CollegeAdminMainScreen.route){
            CollegeAdminMainScreen(context)
        }
        composable(route= AppScreens.CollegeAdLogin.route){
            CollegeAdLogin(AppnavController = navController, remoteRepo)
        }
        composable(route= AppScreens.CollegeAdSignup.route){
            CollegeAdSignup(AppnavController = navController)
        }

    }
}
