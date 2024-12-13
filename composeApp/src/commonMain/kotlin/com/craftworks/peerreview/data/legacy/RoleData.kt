@file:Suppress("PropertyName")

package com.craftworks.peerreview.data.legacy

import kotlinx.serialization.Serializable

enum class PeerReviewRole() {
    STUDENT,
    TEACHER,
    ADMIN
}

@Serializable
data class PeerReviewRoleJsonData(
    val role: Int,
    val course_class_id: Int,
    val website: Int
) : PeerReviewData()


@Serializable
data class CheckRoleResult(
    var peerReviewRoleResponse: PeerReviewRoleResponseJsonData? = null,
    var isCredentialsFound: Boolean
) : PeerReviewData()

@Serializable
data class PeerReviewRoleResponseJsonData(
    val message: String,
    val api_version: String,
    val software_version: String,
    val class_name: String,
    val role: String
) : PeerReviewData()