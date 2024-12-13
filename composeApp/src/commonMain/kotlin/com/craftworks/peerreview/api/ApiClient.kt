package com.craftworks.peerreview.api

import com.craftworks.peerreview.data.PeerReviewRole
import com.craftworks.peerreview.data.User
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.basicAuth
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import java.net.ConnectException
import java.nio.channels.UnresolvedAddressException

//Configure the HttpClient
object ApiClient {
    private var email: String = ""
    private var password: String = ""
    var classId: Int = -1

    var client: HttpClient = createClient()

    @OptIn(ExperimentalSerializationApi::class)
    private fun createClient(): HttpClient {
        return HttpClient(CIO) {
            install(HttpTimeout) {
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
                socketTimeoutMillis = 15000L
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                        explicitNulls = false
                    }
                )
            }

            // If we don't specify error handling, the ktor client will
            // crash the app on any error.
            HttpResponseValidator {
                validateResponse { response ->
                    // Log any non-successful status codes
                    if (!response.status.isSuccess()) {
                        println("Non-successful response: ${response.status.value}")
                    }
                }
                handleResponseExceptionWithRequest { cause, _ ->
                    when (cause) {
                        is ConnectException -> {
                            println("Network Connection Error: ${cause.message}")
                        }
                        is UnresolvedAddressException -> {
                            println("DNS Resolution Error: ${cause.message}")
                        }
                        else -> {
                            println("Unknown Error: ${cause.message}")
                        }
                    }
                }
            }
            expectSuccess = false

            // Set client headers and authentication.
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                basicAuth(
                    username = email,
                    password = password
                )
                accept(ContentType.Application.Json)
            }
        }
    }

    fun setCredentials(user: String, pass: String) {
        email = user
        password = pass

        // Recreate HTTP Client with new credentials
        createClient()
    }
}