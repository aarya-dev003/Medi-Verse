package com.example.medi_verse.App


sealed class AppScreens(val route: String) {
object FirstPage : AppScreens("AppScreens")
object StLogin : AppScreens("StLogin")
object Decision : AppScreens("Decision")
object HomeMainScreen : AppScreens("HomeMainScreen")
object StSignUp : AppScreens("Signup")
object ClubAdSignUp : AppScreens("ClubAdSignUp")
object ClubAdLogin : AppScreens("ClubAdLogin ")
object  ClubAdminMainScreen: AppScreens("ClubAdminMainScreen")
object  CollegeAdminMainScreen: AppScreens("CollegeAdminMainScreen")
object  CollegeAdLogin: AppScreens("CollegeAdLogin")
object  CollegeAdSignup: AppScreens("CollegeAdSignup")
object  SplashScreen: AppScreens("SplashScreen")
object SearchResults: AppScreens("SearchResults")

}