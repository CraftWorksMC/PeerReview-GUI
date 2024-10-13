package com.craftworks.peerreview.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.craftworks.peerreview.api.ApiHelper
import com.craftworks.peerreview.data.PeerReviewAnswerData
import com.craftworks.peerreview.login.LoginManager
import com.craftworks.peerreview.ui.elements.ScreenHeader
import com.craftworks.peerreview.ui.elements.StudentQuestionGrade
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.stringResource
import peerreview.composeapp.generated.resources.Res
import peerreview.composeapp.generated.resources.header_grades

@Composable
fun StudentGradeScreen(lessonId: Int) {
    val studentGrades = remember { mutableStateListOf<PeerReviewAnswerData>() }

    LaunchedEffect(Unit) {
        val studentGradesUrl = ApiHelper.getAnswerStudentsDone(
            LoginManager.guidToken, lessonId, LoginManager.role, 8
        )

        println(studentGradesUrl)

        val studentGradesData = ApiHelper.sendApiRequestGET(studentGradesUrl)

        studentGradesData.body?.string()?.let {
            println(it)
            val lessons = Json.decodeFromString<List<PeerReviewAnswerData>>(it)
            studentGrades.addAll(lessons)
        }

        println(studentGrades[0])
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(bottom = 6.dp)
    ) {
        item {
            ScreenHeader(stringResource(Res.string.header_grades))
        }

        items(studentGrades) { question ->
            StudentQuestionGrade(question)
        }
    }
}