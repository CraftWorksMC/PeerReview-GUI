package com.craftworks.peerreview.data

import kotlinx.serialization.Serializable

enum class PeerReviewRole() {
    STUDENT,
    TEACHER,
    ADMIN
}

@Serializable
data class User (
    val name: String,
    val email: String,
    val password: String,
    val classId: Int,
    val role: PeerReviewRole,
): PeerReviewData()