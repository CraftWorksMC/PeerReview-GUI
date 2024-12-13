@file:Suppress("PropertyName")

package com.craftworks.peerreview.data.legacy

import kotlinx.serialization.Serializable

@Serializable
data class PeerReviewFeedbackDataJson(
    val lesson_id: Int,
    val id: Int,
    val feedback_text: String?,
    val grade: Float,
    val missing_elements: String?,
    val role: Int,
    val website: Int,
    val token: String,
    val is_chat_gpt: Int
) : PeerReviewData()

@Serializable
data class PeerReviewAnswerForFeedbackData(
    var id: Int,
    val question: String?,
    val answer_text: String?
)