package com.craftworks.peerreview.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftworks.peerreview.api.ApiRepository
import com.craftworks.peerreview.data.Lesson
import io.ktor.client.call.body
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class LessonsViewmodel : ViewModel(), ReloadableViewModel {
    private val _studentLessons = MutableStateFlow<List<Lesson>>(mutableListOf())
    val studentLessons: StateFlow<List<Lesson>> = _studentLessons.asStateFlow()
    
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
                    async { ApiRepository().getLessons() }

                val lessonSummaryData = lessonSummaryDataDeferred.await()

                lessonSummaryData.onSuccess {
                    val lessons = Json.decodeFromString<List<Lesson>>(it.body())
                    _studentLessons.value = lessons
                    println("Student Lessons JSON: ${_studentLessons.value}")
                }.onFailure {
                      println("Error getting student lessons: ${it.message}")
                }
            }
        }
    }
}