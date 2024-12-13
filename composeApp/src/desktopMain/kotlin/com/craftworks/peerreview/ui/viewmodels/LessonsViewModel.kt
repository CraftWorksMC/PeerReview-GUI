package com.craftworks.peerreview.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftworks.peerreview.api.ApiClient
import com.craftworks.peerreview.api.ApiRepository
import com.craftworks.peerreview.api.ApiRoutes
import com.craftworks.peerreview.data.legacy.StudentLessonsTableData
import io.ktor.client.statement.bodyAsText
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
    
    init {
        getStudentCredentials()
    }

    override fun reloadData() {
        getStudentCredentials()
    }

    private fun getStudentCredentials() {
        viewModelScope.launch {
            coroutineScope {
                val lessonSummaryDataDeferred =
                    async { ApiRepository().getLessons(ApiClient.classId) }

                val lessonSummaryData = lessonSummaryDataDeferred.await()

                lessonSummaryData.onSuccess {
                    val lessons = Json.decodeFromString<List<StudentLessonsTableData>>(it.bodyAsText())
                    _studentLessons.value = lessons
                    println("Student Lessons JSON: ${_studentLessons.value}")
                }.onFailure {
                      println("Error getting student lessons: ${it.message}")
                }
            }
        }
    }
}