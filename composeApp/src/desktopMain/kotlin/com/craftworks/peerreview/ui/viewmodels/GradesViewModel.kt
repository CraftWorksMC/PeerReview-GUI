package com.craftworks.peerreview.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftworks.peerreview.data.legacy.PeerReviewAnswerData
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GradesViewmodel() : ViewModel(), ReloadableViewModel {
    private val _currentLessonId = MutableStateFlow(-1)
    val currentLessonId: StateFlow<Int> = _currentLessonId.asStateFlow()

    private val _studentGrades = MutableStateFlow<List<PeerReviewAnswerData>>(mutableListOf())
    val studentGrades: StateFlow<List<PeerReviewAnswerData>> = _studentGrades.asStateFlow()

    override fun reloadData() {
        getStudentGrades()
    }

    private fun getStudentGrades() {
        viewModelScope.launch {
            coroutineScope {
//                _studentGrades.value = mutableListOf()
//
//                val studentGradesUrl = ApiHelper.getAnswerStudentsDone(
//                    LoginManager.guidToken, currentLessonId.value, LoginManager.role, 8
//                )
//
//                val studentGradesDataDeferred =
//                    async { ApiHelper.sendApiRequestGET(studentGradesUrl) }
//
//                val studentGradesData = studentGradesDataDeferred.await()
//
//                if (studentGradesData.isSuccessful) {
//                    studentGradesData.body?.string()?.let {
//                        println(it)
//                        val grades = Json.decodeFromString<List<PeerReviewAnswerData>>(it)
//                        _studentGrades.value = grades
//                    }
//
//                    println("Student Grades JSON: ${_studentGrades.value}")
//                }
            }
        }
    }

    fun updateLessonId(newId: Int) {
        _currentLessonId.value = newId
        getStudentGrades()
    }
}