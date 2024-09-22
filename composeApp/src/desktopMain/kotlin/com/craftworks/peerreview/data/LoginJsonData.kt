package com.craftworks.peerreview.data

import kotlinx.serialization.Serializable

@Serializable
data class LoginJsonData(
    var email: String,
    var password: String,
    var website: Int
): PeerReviewData()