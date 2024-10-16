package com.craftworks.peerreview.data

import kotlinx.serialization.Serializable

@Serializable
data class PeerReviewLessonData(
    val id: Int,
    val title: String,
    val created_at: String,
    val first_deadline: String?,
    val second_deadline: String?,
    val content_html: String?,
    val class_id: Int,
    val lesson_questions: List<PeerReviewQuestionData>,
    val reservations_list_json: String?
)

@Serializable
data class PeerReviewQuestionData(
    val id: Int,
    val question_text: String,
    val created_at: String?,
    val answers: List<PeerReviewAnswerData>?,
    val class_id: Int,
    val answer: String?,
    val answer_review_status: Int
)