package com.craftworks.peerreview.navigation

sealed class Screen(val route: String) {
    data object S_Lessons : Screen("s_lessons_screen")
    data object S_Grades : Screen("s_grades_screen")
}