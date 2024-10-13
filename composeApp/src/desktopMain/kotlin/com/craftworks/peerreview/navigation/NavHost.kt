package com.craftworks.peerreview.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.craftworks.peerreview.ui.StudentGradeScreen
import com.craftworks.peerreview.ui.StudentLessonsScreen
import com.craftworks.peerreview.ui.theme.peerReviewColorScheme

@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.S_Grades.route,
        enterTransition = {
            scaleIn(tween(300), 0.95f) + fadeIn(tween(400))
        },
        exitTransition = {
            fadeOut(tween(400))
        },
        modifier = Modifier.fillMaxSize().padding(12.dp).clip(RoundedCornerShape(24.dp))
            .background(peerReviewColorScheme.background)
    ) {
        println("Recomposing NavHost!")

        //val currentLessonId by mutableStateOf(0)

        composable(route = Screen.S_Lessons.route) {
            StudentLessonsScreen()
        }

        composable(route = Screen.S_Grades.route) {
            StudentGradeScreen(9)
        }
    }
}