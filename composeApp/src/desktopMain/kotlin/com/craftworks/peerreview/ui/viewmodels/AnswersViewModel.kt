package com.craftworks.peerreview.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftworks.peerreview.api.ApiHelper
import com.craftworks.peerreview.data.PeerReviewLessonData
import com.craftworks.peerreview.login.LoginManager
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class AnswersViewModel() : ViewModel(), ReloadableViewModel {
    private val _studentQuestions = MutableStateFlow<List<PeerReviewLessonData>>(mutableListOf())
    val studentQuestions: StateFlow<List<PeerReviewLessonData>> = _studentQuestions.asStateFlow()

    init {
        getStudentQuestions()
    }

    override fun reloadData() {
        getStudentQuestions()
    }

    private fun getStudentQuestions() {
        viewModelScope.launch {
            coroutineScope {
                val studentQuestionsUrl = ApiHelper.getStudentToDoQuestions(
                    LoginManager.guidToken, LoginManager.courseId, LoginManager.role, 8
                )

                val studentQuestionsDataDeferred =
                    async { ApiHelper.sendApiRequestGET(studentQuestionsUrl) }

                val studentQuestionsData = studentQuestionsDataDeferred.await()

                studentQuestionsData.body?.string()?.let {
                    val questions = Json.decodeFromString<List<PeerReviewLessonData>>(it)
                    _studentQuestions.value = questions
                }

                println("Student Questions To Answer JSON: ${_studentQuestions.value}")
            }
        }
    }
}