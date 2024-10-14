package com.craftworks.peerreview.data

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Credentials(
    val email: String,
    var password: String,
    val courseID: String,
    val role: PeerReviewRole,
    var isCredentialFileExist: Boolean = false
) : PeerReviewData()

@Serializable
data class LoginJsonData(
    var email: String,
    var password: String,
    var website: Int
) : PeerReviewData()

@Serializable
data class LoginResultData(
    var guidToken: String = UUID.randomUUID().toString(),
    var swVersion: PeerReviewRoleResponseJsonData? = null,
    var courseName: String? = null
) : PeerReviewData()