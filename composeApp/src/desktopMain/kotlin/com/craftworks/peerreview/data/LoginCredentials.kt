package com.craftworks.peerreview.data

import kotlinx.serialization.Serializable

@Serializable
data class Credentials(
    val email: String,
    var password: String,
    val courseID: String,
    val role: PeerReviewRole,
    var isCredentialFileExist: Boolean = false
): PeerReviewData()