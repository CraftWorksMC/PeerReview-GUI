package com.craftworks.peerreview.api

object ApiRoutes {
    private const val IS_DEBUG_API: Boolean = true
    private val BASE_URL:String = if (IS_DEBUG_API) "http://0.0.0.0:8080" else "https://api.peerreview.craftworks.top"
    val GET_AUTH = "$BASE_URL/users/1"
    val POST_REGISTER = "$BASE_URL/register" // Assuming this route remains the same
    val POST_CREATE_CLASS = "$BASE_URL/classes/createClass"
    val POST_ADD_STUDENT = "$BASE_URL/classes/addStudent/{classId}"
    val GET_LESSONS = "$BASE_URL/lessons/getLessons"
    val GET_LESSON_QUESTIONS = "$BASE_URL/lessons/getQuestions/{lessonId}"
    val POST_CREATE_LESSON = "$BASE_URL/lessons/createLesson"
    val POST_ANSWER_LESSON = "$BASE_URL/lessons/answer/{lessonId}"
}