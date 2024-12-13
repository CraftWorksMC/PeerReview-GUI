package com.craftworks.peerreview.api

object ApiRoutes {
    private const val IS_DEBUG_API: Boolean = false
    private val BASE_URL:String = if (IS_DEBUG_API) "http://0.0.0.0:8080" else "https://api.peerreview.craftworks.top"
    val GET_AUTH = "$BASE_URL/users/1"
    val POST_REGISTER = "$BASE_URL/register"
    val GET_LESSONS = "$BASE_URL/lessons/{classId}/getLessons"
}