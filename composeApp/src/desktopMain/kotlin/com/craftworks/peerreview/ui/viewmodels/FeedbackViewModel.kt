package com.craftworks.peerreview.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftworks.peerreview.data.legacy.PeerReviewAnswerForFeedbackData
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FeedbackViewModel : ViewModel(), ReloadableViewModel {
    private val _currentLessonId = MutableStateFlow(-1)
    val currentLessonId: StateFlow<Int> = _currentLessonId.asStateFlow()

    private val _studentFeedback = MutableStateFlow<PeerReviewAnswerForFeedbackData?>(null)
    val studentFeedback: StateFlow<PeerReviewAnswerForFeedbackData?> =
        _studentFeedback.asStateFlow()

    override fun reloadData() {
        getStudentFeedback()
    }

    fun getStudentFeedback() {
        println("GETTING FEEDBACK!")
        viewModelScope.launch {
            coroutineScope {
//                _studentFeedback.value = null
//
//                val studentFeedbackUrl = ApiHelper.getFeedback(
//                    LoginManager.guidToken, _currentLessonId.value, LoginManager.role, 8
//                )
//
//                val studentFeedbackDataDeferred =
//                    async { ApiHelper.sendApiRequestGET(studentFeedbackUrl) }
//
//                val studentFeedbackData = studentFeedbackDataDeferred.await()
//
//                if (studentFeedbackData.isSuccessful) {
//                    studentFeedbackData.body?.string()?.let {
//                        val feedback = Json.decodeFromString<PeerReviewAnswerForFeedbackData>(it)
//                        _studentFeedback.value = feedback
//                    }
//                    println("Student Feedback JSON: ${_studentFeedback.value}")
//                }
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
//        val peerReviewFeedbackData = PeerReviewFeedbackDataJson(
//            lesson_id = _currentLessonId.value,
//            id = questionId,
//            feedback_text = feedback,
//            grade = grade,
//            missing_elements = missingElements,
//            role = PeerReviewRole.STUDENT.ordinal + 1,
//            website = 8,
//            token = "",
//            is_chat_gpt = if (isChatGpt) 1 else 0
//        )
//
//        ApiHelper.sendApiRequestPOST(peerReviewFeedbackData, ApiHelper.postFeedback())
    }

    fun updateLessonId(newId: Int) {
        _currentLessonId.value = newId
        getStudentFeedback()
    }
}