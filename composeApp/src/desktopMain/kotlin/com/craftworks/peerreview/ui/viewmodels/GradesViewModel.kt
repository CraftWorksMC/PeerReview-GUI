package com.craftworks.peerreview.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftworks.music.ui.viewmodels.ReloadableViewModel
import com.craftworks.peerreview.api.ApiHelper
import com.craftworks.peerreview.data.PeerReviewAnswerData
import com.craftworks.peerreview.login.LoginManager
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class GradesViewmodel() : ViewModel(), ReloadableViewModel {
    private val _currentLessonId = MutableStateFlow(9)
    val currentLessonId: StateFlow<Int> = _currentLessonId.asStateFlow()

    private val _studentGrades = MutableStateFlow<List<PeerReviewAnswerData>>(mutableListOf())
    val studentGrades: StateFlow<List<PeerReviewAnswerData>> = _studentGrades.asStateFlow()

    init {
        getStudentCredentials()
    }

    override fun reloadData() {
        getStudentCredentials()
    }

    private fun getStudentCredentials() {
        viewModelScope.launch {
            coroutineScope {
                val studentGradesUrl = ApiHelper.getAnswerStudentsDone(
                    LoginManager.guidToken, currentLessonId.value, LoginManager.role, 8
                )

                //val lessonSummaryData = ApiHelper.sendApiRequestGET(lessonSummaryUrl)
                val studentGradesDataDeferred =
                    async { ApiHelper.sendApiRequestGET(studentGradesUrl) }

                val studentGradesData = studentGradesDataDeferred.await()

                studentGradesData.body?.string()?.let {
                    println(it)
                    val grades = Json.decodeFromString<List<PeerReviewAnswerData>>(it)
                    _studentGrades.value = grades
                }

                println("Student Grades JSON: ${_studentGrades.value}")
            }
        }
    }
}