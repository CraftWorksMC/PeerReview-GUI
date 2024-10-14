package com.craftworks.peerreview.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftworks.music.ui.viewmodels.ReloadableViewModel
import com.craftworks.peerreview.api.ApiHelper
import com.craftworks.peerreview.data.StudentLessonsTableData
import com.craftworks.peerreview.login.LoginManager
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class LessonsViewmodel() : ViewModel(), ReloadableViewModel {
    private val _studentLessons = MutableStateFlow<List<StudentLessonsTableData>>(mutableListOf())
    val studentLessons: StateFlow<List<StudentLessonsTableData>> = _studentLessons.asStateFlow()

//    private val _password = MutableStateFlow("")
//    val password: StateFlow<String> = _password.asStateFlow()
//
//    private val _courseId = MutableStateFlow("")
//    val courseId: StateFlow<String> = _courseId.asStateFlow()
//
//    private val _role = MutableStateFlow(PeerReviewRole.STUDENT)
//    val role: StateFlow<PeerReviewRole> = _role.asStateFlow()

    init {
        getStudentCredentials()
    }

    override fun reloadData() {
        getStudentCredentials()
    }

    private fun getStudentCredentials() {
        viewModelScope.launch {
            coroutineScope {
                val lessonSummaryUrl = ApiHelper.getLessonsSummary(
                    LoginManager.guidToken, LoginManager.courseId, LoginManager.role, 8
                )

                //val lessonSummaryData = ApiHelper.sendApiRequestGET(lessonSummaryUrl)
                val lessonSummaryDataDeferred =
                    async { ApiHelper.sendApiRequestGET(lessonSummaryUrl) }

                val lessonSummaryData = lessonSummaryDataDeferred.await()

                lessonSummaryData.body?.string()?.let {
                    val lessons = Json.decodeFromString<List<StudentLessonsTableData>>(it)
                    _studentLessons.value = lessons
                }

                println("Student Lessons JSON: ${_studentLessons.value}")
            }
        }
    }
}