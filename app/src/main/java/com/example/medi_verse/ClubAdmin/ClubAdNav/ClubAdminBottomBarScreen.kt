package com.example.medi_verse.ClubAdmin.ClubAdNav

import com.example.medi_verse.R

sealed class ClubAdminBottomBarScreen (
    val route:String,
    val title:String,
    val drawableId: Int
){
    object Home : ClubAdminBottomBarScreen(
        route = "home",
        title = "Home",
        drawableId = R.drawable.home
    )
    object AddPost: ClubAdminBottomBarScreen(
        route = "addpost",
        title = "AddPost",
        drawableId = R.drawable.addbutton
    )
    object Feedback: ClubAdminBottomBarScreen(
        route = "feedback",
        title = "Feedback",
        drawableId = R.drawable.feedback
    )
}