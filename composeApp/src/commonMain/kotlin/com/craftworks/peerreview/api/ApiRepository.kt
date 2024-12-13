package com.craftworks.peerreview.api

import com.craftworks.peerreview.api.ApiClient.client
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

    suspend fun getLessons(classId: Int): Result<HttpResponse> = runCatching {
        client.get(ApiRoutes.GET_LESSONS.replace("{classId}", classId.toString()))
    }
}