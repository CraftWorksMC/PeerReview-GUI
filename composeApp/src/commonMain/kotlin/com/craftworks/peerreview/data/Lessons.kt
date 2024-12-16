package com.craftworks.peerreview.data

import kotlinx.serialization.Serializable

@Serializable
data class Lesson(
    val id: Int,
    val title: String,
    val description: String,
    val createdBy: String = "",
    val classId: Int,
    val createdAt: String,
    val deadline: String,
    val questions: List<String> = emptyList()
): PeerReviewData()