package com.example.medi_verse.presentation.Student.StNav

import com.example.medi_verse.R


sealed class HomeBottomBarScreen(
    val route: String,
    val title: String,
    val drawableId: Int
) {
    object Home : HomeBottomBarScreen(
        route = "home",
        title = "Home",
        drawableId = R.drawable.home
    )

    object Announcements : HomeBottomBarScreen(
        route = "announcements",
        title = "Announcements",
        drawableId = R.drawable.annoucements
    )

    object Feedback : HomeBottomBarScreen(
        route = "feedback",
        title = "Feedback",
        drawableId = R.drawable.feedback

    )
    object StLogin : HomeBottomBarScreen(
        route = "StLogin",
        title = "",
        drawableId = 0

    )
}
