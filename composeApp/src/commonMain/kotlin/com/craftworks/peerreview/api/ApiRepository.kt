package com.craftworks.peerreview.api

import com.craftworks.peerreview.api.ApiClient.client
import com.craftworks.peerreview.data.Answer
import com.craftworks.peerreview.data.Lesson
import com.craftworks.peerreview.data.User
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ApiRepository {
    suspend fun getAuth(): Result<HttpResponse> = runCatching { client.get(ApiRoutes.GET_AUTH) }

    suspend fun postRegister(user: User): Result<HttpResponse> = runCatching {
        client.post(ApiRoutes.POST_REGISTER) {
            contentType(ContentType.Application.Json)
            setBody(user)
        }
    }

    suspend fun createClass(className: String): Result<HttpResponse> = runCatching {
        client.post(ApiRoutes.POST_CREATE_CLASS) {
            contentType(ContentType.Application.Json)
            setBody(className)
        }
    }

    suspend fun addStudentsToClass(classId: Int, studentNames: List<String>? = null, studentIds: List<Int>? = null): Result<HttpResponse> = runCatching {
        client.post(ApiRoutes.POST_ADD_STUDENT.replace("{classId}", classId.toString())) {
            contentType(ContentType.Application.Json)
            // Accept either student names or ids. If both are provided, use the names.
            setBody(studentNames ?: studentIds)
        }
    }

    suspend fun getLessons(): Result<HttpResponse> = runCatching {
        client.get(ApiRoutes.GET_LESSONS)
    }

    suspend fun getLessonQuestions(lessonId: Int): Result<HttpResponse> = runCatching {
        client.get(ApiRoutes.GET_LESSON_QUESTIONS.replace("{lessonId}", lessonId.toString()))
    }

    suspend fun createLesson(lesson: Lesson): Result<HttpResponse> = runCatching {
        client.post(ApiRoutes.POST_CREATE_LESSON) {
            contentType(ContentType.Application.Json)
            setBody(lesson)
        }
    }

    suspend fun answerLesson(lessonId: Int, answer: Answer): Result<HttpResponse> = runCatching {
        client.post(ApiRoutes.POST_ANSWER_LESSON.replace("{lessonId}", lessonId.toString())) {
            contentType(ContentType.Application.Json)
            setBody(answer)
        }
    }
}