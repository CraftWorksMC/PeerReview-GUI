package com.craftworks.peerreview.data

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class LoginResultData(
    var guidToken: String = UUID.randomUUID().toString(),
    var swVersion: PeerReviewRoleResponseJsonData? = null,
    var courseName: String? = null
): PeerReviewData()