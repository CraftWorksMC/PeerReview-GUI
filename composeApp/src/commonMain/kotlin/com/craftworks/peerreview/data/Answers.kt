package com.craftworks.peerreview.data

import kotlinx.serialization.Serializable

@Serializable
data class Answer(
    val id: Int,
    val questionId: Int,
    val createdAt: String,
    val createdBy: String,
    val answer: String
): PeerReviewData()