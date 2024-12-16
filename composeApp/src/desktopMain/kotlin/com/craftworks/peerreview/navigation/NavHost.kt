package com.craftworks.peerreview.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.craftworks.peerreview.ui.StudentAnswerScreen
import com.craftworks.peerreview.ui.StudentGiveFeedbackScreen
import com.craftworks.peerreview.ui.StudentGradeScreen
import com.craftworks.peerreview.ui.StudentLessonsScreen
import com.craftworks.peerreview.ui.viewmodels.AnswersViewModel
import com.craftworks.peerreview.ui.viewmodels.FeedbackViewModel
import com.craftworks.peerreview.ui.viewmodels.GradesViewmodel
import com.craftworks.peerreview.ui.viewmodels.LessonsViewmodel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {
    println("Initialized NavGraph")

    val lessonsViewmodel = remember { LessonsViewmodel() }
    val questionsViewmodel = remember { AnswersViewModel() }
    val feedbackViewmodel = remember { FeedbackViewModel() }
    val gradesViewmodel = remember { GradesViewmodel() }

    NavHost(
        navController = navController,
        startDestination = Screen.S_Lessons.route,
        enterTransition = {
            scaleIn(tween(300), 0.95f) + fadeIn(tween(400))
        },
        exitTransition = {
            fadeOut(tween(400))
        },
        modifier = Modifier.fillMaxSize().padding(12.dp).clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.background)
    ) {
        println("Recomposing NavHost!")

        composable(route = Screen.S_Lessons.route) {
            StudentLessonsScreen(lessonsViewmodel, navController)
        }

        composable(route = Screen.S_Answer.route) {
            StudentAnswerScreen(questionsViewmodel)
        }

        composable(
            route = Screen.S_Grades.getRoute(),
            arguments = listOf(navArgument("lessonId") { type = NavType.IntType })
        ) {
            val lessonId = remember { it.arguments?.getInt("lessonId") ?: 0 }
            if (gradesViewmodel.currentLessonId.value != lessonId)
                gradesViewmodel.updateLessonId(lessonId)
            StudentGradeScreen(gradesViewmodel, navController)
        }

        composable(
            route = Screen.S_Feedback.getRoute(),
            arguments = listOf(navArgument("lessonId") { type = NavType.IntType })
        ) {
            val lessonId = remember { it.arguments?.getInt("lessonId") ?: 0 }
            if (feedbackViewmodel.currentLessonId.value != lessonId)
                feedbackViewmodel.updateLessonId(lessonId)
            StudentGiveFeedbackScreen(feedbackViewmodel, navController)
        }
    }
}