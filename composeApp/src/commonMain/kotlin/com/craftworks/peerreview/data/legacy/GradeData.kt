@file:Suppress("PropertyName")

package com.craftworks.peerreview.data.legacy

import kotlinx.serialization.Serializable

@Serializable
data class PeerReviewAnswerData(
    val id: Int,
    val question_id: Int,
    val user_id: Int,
    val answer_text: String,
    val created_at: String?,
    val feedbacks: List<PeerReviewFeedbackData>,
    val question_text: String,
    val is_chat_gpt: Int
)

@Serializable
data class PeerReviewFeedbackData(
    val id: Int,
    val user_id: Int,
    val feedback_text: String,
    val grade: Float,
    val missing_elements: String,
    val answer_id: Int,
    val created_at: String?,
    val is_chat_gpt: Int,
    val feedback_role: Int
)