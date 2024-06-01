package com.example.medi_verse.presentation.CollegeAdmin.CollegeAdNav

import com.example.medi_verse.R

sealed class CollegeAdBottomBarScreen(
    val route:String,
    val title:String,
    val drawableId: Int
) {
    object Home : CollegeAdBottomBarScreen(
        route = "home",
        title = "Home",
        drawableId = R.drawable.home
    )

    object Announcements : CollegeAdBottomBarScreen(
        route = "announcements",
        title = "Announcements",
        drawableId = R.drawable.annoucements
    )
    object RegisterClubAdmin : CollegeAdBottomBarScreen(
        route = "RegisterClubAdmin",
        title = "Register",
        drawableId = R.drawable.register
    )
}