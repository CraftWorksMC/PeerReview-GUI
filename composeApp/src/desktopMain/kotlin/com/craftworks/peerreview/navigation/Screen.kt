package com.craftworks.peerreview.navigation

sealed class Screen(val route: String) {
    data object S_Lessons : Screen("s_lessons_screen")
    data object S_Answer : Screen("s_answer_screen")

    //data object S_Grades : Screen("s_grades_screen")
    data class S_Grades(val lessonId: Int) : Screen("s_grades_screen/{lessonId}") {
        companion object {
            fun getRoute(): String = "s_grades_screen/{lessonId}"
            fun createRoute(lessonId: Int) = "s_grades_screen/$lessonId"
        }
    }
}