package com.craftworks.peerreview.api

import androidx.compose.ui.text.capitalize
import com.craftworks.peerreview.data.PeerReviewData
import com.craftworks.peerreview.data.PeerReviewRole
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.util.Locale
import java.util.UUID

object ApiHelper {

    // Imposta il client globale con cookie
    private val client = OkHttpClient.Builder().cookieJar(object : CookieJar {
        private val cookieStore = mutableMapOf<String, List<Cookie>>()

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            cookieStore[url.host] = cookies
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            return cookieStore[url.host] ?: emptyList()
        }
    }).build()

    fun sendApiRequest(
        data: PeerReviewData? = null,
        postUrl: String,
    ): Response {
        val request = Request.Builder().url(getApiBase() + postUrl)

        if (data != null){
            // Encode data to json
            val json = Json.encodeToString(data)
            val body = json.toRequestBody("application/json".toMediaTypeOrNull())
            //println(json)

            request.post(body)
        }

        // Try sending request.
        try {
            val connection = client.newCall(request.build()).execute()
            //println("message: ${connection.message}, body: ${connection.body?.string()}, request: ${connection.request}")
            return connection
        } catch (e: Exception) {
            //loginStatus.value = e.message.toString();
            return Response.Builder().body(e.message?.toResponseBody()).build()
        }
    }

    //region Endpoint API

    private fun getApiBase(debugMode: Boolean = false): String {
        return if (debugMode) {
            "https://localhost:44391/api/v1/"
        } else {
            "https://www.apibaobab.com/api/v1/"
        }
    }

    fun getClass(guid: UUID, classId: Int, userType: PeerReviewRole, website: Int = 8): String {
        return "PeerReview/Class/$website/${userType.ordinal}/$classId/$guid"
    }

    fun getStudents(token: UUID, courseId: Int, role: PeerReviewRole, website: Int = 8): String {
        return "PeerReview/Students/$website/${role.ordinal}/$courseId/$token"
    }

    fun getStudentToDoQuestions(token: UUID, courseId: Int, role: PeerReviewRole, website: Int = 8): String {
        return "PeerReview/Question/Students/ToDoQuestions/$website/${role.ordinal}/$courseId/$token"
    }

    fun getTeacherQuestionsToMark(token: UUID, courseId: Int, role: PeerReviewRole, website: Int = 8): String {
        return "PeerReview/Question/Teacher/QuestionsToMark/$website/${role.ordinal}/$courseId/$token"
    }

    fun getFeedback(token: UUID, lessonId: Int, role: PeerReviewRole, website: Int = 8): String {
        return "PeerReview/Feedback/$website/${role.ordinal}/$lessonId/$token"
    }

    fun postFeedback(): String {
        return "PeerReview/Feedback"
    }

    fun postRole(): String {
        return "PeerReview/Role"
    }

    fun postLogin(): String {
        return "Login"
    }

    fun getLessonsSummary(token: String, courseId: String, role: PeerReviewRole, website: Int = 8): String {
        return "PeerReview/Lessons/Summary/${role.name.lowercase().capitalize()}/$website/${role.ordinal + 1}/$courseId/$token"
    }

    fun getAnswerStudentsDone(token: UUID, lessonId: Int, role: PeerReviewRole, website: Int = 8): String {
        return "PeerReview/Answer/Lesson/$website/${role.ordinal}/$lessonId/$token"
    }

    fun postEnroll(): String {
        return "PeerReview/Enroll"
    }

    fun postLessons(): String {
        return "PeerReview/Lessons"
    }

    fun postQuestion(): String {
        return "PeerReview/Question"
    }

    //endregion

}