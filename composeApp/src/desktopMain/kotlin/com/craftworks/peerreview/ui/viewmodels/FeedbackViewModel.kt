package com.craftworks.peerreview.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftworks.peerreview.api.ApiHelper
import com.craftworks.peerreview.data.PeerReviewAnswerForFeedbackData
import com.craftworks.peerreview.data.PeerReviewFeedbackDataJson
import com.craftworks.peerreview.data.PeerReviewRole
import com.craftworks.peerreview.login.LoginManager
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class FeedbackViewModel() : ViewModel(), ReloadableViewModel {
    private val _currentLessonId = MutableStateFlow(14)
    val currentLessonId: StateFlow<Int> = _currentLessonId.asStateFlow()

    private val _studentFeedback = MutableStateFlow<PeerReviewAnswerForFeedbackData?>(null)
    val studentFeedback: StateFlow<PeerReviewAnswerForFeedbackData?> =
        _studentFeedback.asStateFlow()

    init {
        getStudentFeedback()
    }

    override fun reloadData() {
        getStudentFeedback()
    }

    private fun getStudentFeedback() {
        viewModelScope.launch {
            coroutineScope {
                val studentFeedbackUrl = ApiHelper.getFeedback(
                    LoginManager.guidToken, _currentLessonId.value, LoginManager.role, 8
                )

                val studentFeedbackDataDeferred =
                    async { ApiHelper.sendApiRequestGET(studentFeedbackUrl) }

                val studentFeedbackData = studentFeedbackDataDeferred.await()

                studentFeedbackData.body?.string()?.let {
                    val feedback = Json.decodeFromString<PeerReviewAnswerForFeedbackData>(it)
                    _studentFeedback.value = feedback
                }

                println("Student Feedback JSON: ${_studentFeedback.value}")
            }
        }
    }

    fun sendFeedback(
        questionId: Int,
        feedback: String,
        missingElements: String,
        grade: Float,
        isChatGpt: Boolean
    ) {
        val peerReviewFeedbackData = PeerReviewFeedbackDataJson(
            lesson_id = _currentLessonId.value,
            id = questionId,
            feedback_text = feedback,
            grade = grade,
            missing_elements = missingElements,
            role = PeerReviewRole.STUDENT,
            website = 8,
            token = LoginManager.guidToken,
            is_chat_gpt = if (isChatGpt) 1 else 0
        )

        ApiHelper.sendApiRequestPOST(peerReviewFeedbackData, ApiHelper.postFeedback())
    }

    fun updateLessonId(newId: Int) {
        _currentLessonId.value = newId
        getStudentFeedback()
    }
}