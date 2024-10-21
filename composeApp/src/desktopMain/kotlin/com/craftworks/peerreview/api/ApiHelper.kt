package com.craftworks.peerreview.api

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

object ApiHelper {

    // Imposta il client globale con cookie
    private val cookieJar = object : CookieJar {
        private val cookieStore: MutableMap<String, MutableList<Cookie>> = mutableMapOf()

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            // Get the existing cookies for the host, if any
            val existingCookies = cookieStore[url.host] ?: mutableListOf()

            // Add new cookies, replacing any existing cookies with the same name
            for (newCookie in cookies) {
                val cookieIndex = existingCookies.indexOfFirst { it.name == newCookie.name }
                if (cookieIndex != -1) {
                    existingCookies[cookieIndex] = newCookie
                } else {
                    existingCookies.add(newCookie)
                }
            }

            // Update the cookie store for this host
            cookieStore[url.host] = existingCookies
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            // Return cookies for the current host, or an empty list if none exist
            return cookieStore[url.host] ?: mutableListOf()
        }
    }

    private val client = OkHttpClient.Builder()
        .cookieJar(cookieJar).build()

    fun sendApiRequestPOST(
        data: PeerReviewData? = null,
        postUrl: String,
    ): Response {

        // Encode data to json
        val json = Json.encodeToString(data)
        val body = json.toRequestBody("application/json".toMediaTypeOrNull())

        println("JSON Body for url $postUrl: \n $json")

        val request = Request.Builder().url(getApiBase() + postUrl).post(body)

        // Try sending request.
        return try {
            client.newCall(request.build()).execute()
        } catch (e: Exception) {
            Response.Builder().body(e.message?.toResponseBody()).build()
        }
    }

    fun sendApiRequestGET(
        getUrl: String,
    ): Response {
        val request = Request.Builder().url(getApiBase() + getUrl).build()

        // Try sending request.
        return try {
            client.newCall(request).execute()
        } catch (e: Exception) {
            Response.Builder().body(e.message?.toResponseBody()).build()
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

    fun getClass(guid: String, classId: Int, userType: PeerReviewRole, website: Int = 8): String {
        return "PeerReview/Class/$website/${userType.ordinal + 1}/$classId/$guid"
    }

    fun getStudents(token: String, courseId: Int, role: PeerReviewRole, website: Int = 8): String {
        return "PeerReview/Students/$website/${role.ordinal + 1}/$courseId/$token"
    }

    fun getStudentToDoQuestions(
        token: String,
        courseId: String,
        role: PeerReviewRole,
        website: Int = 8
    ): String {
        return "PeerReview/Question/Students/ToDoQuestions/$website/${role.ordinal + 1}/$courseId/$token"
    }

    fun getTeacherQuestionsToMark(
        token: String,
        courseId: Int,
        role: PeerReviewRole,
        website: Int = 8
    ): String {
        return "PeerReview/Question/Teacher/QuestionsToMark/$website/${role.ordinal}/$courseId/$token"
    }

    fun getFeedback(token: String, lessonId: Int, role: PeerReviewRole, website: Int = 8): String {
        return "PeerReview/Feedback/$website/${role.ordinal + 1}/$lessonId/$token"
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

    fun getLessonsSummary(
        token: String,
        courseId: String,
        role: PeerReviewRole,
        website: Int = 8
    ): String {
        return "PeerReview/Lessons/Summary/${
            role.name.lowercase().capitalize()
        }/$website/${role.ordinal + 1}/$courseId/$token"
    }

    fun getAnswerStudentsDone(
        token: String,
        lessonId: Int,
        role: PeerReviewRole,
        website: Int = 8
    ): String {
        return "PeerReview/Answer/Lesson/$website/${role.ordinal + 1}/$lessonId/$token"
    }

    fun postAnswer(): String {
        return "PeerReview/Answer"
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