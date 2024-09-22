package com.craftworks.peerreview.navigation

sealed class Screen(val route: String) {
    data object Lessons : Screen("lessons_screen")
}