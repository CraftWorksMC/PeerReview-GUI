package com.craftworks.peerreview.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.craftworks.peerreview.api.ApiHelper
import com.craftworks.peerreview.data.CheckRoleResult
import com.craftworks.peerreview.data.Credentials
import com.craftworks.peerreview.data.LoginJsonData
import com.craftworks.peerreview.data.LoginResultData
import com.craftworks.peerreview.data.PeerReviewRole
import com.craftworks.peerreview.data.PeerReviewRoleJsonData
import com.craftworks.peerreview.saveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.Response
import java.util.UUID


/*
class LoginManager {

    companion object {
        var loginStatus by mutableStateOf("")
        var isLoggedIn by mutableStateOf(false)
        var isStudentEnrolled = mutableStateOf(false)

        var guidToken by mutableStateOf("")
        var courseId by mutableStateOf("")
        var role by mutableStateOf(PeerReviewRole.STUDENT)

        var className by mutableStateOf("")

        var maxAttempts = 3
    }

    suspend fun attemptLoginAsync(
        credentials: Credentials
    ): Result<LoginResultData> = withContext(Dispatchers.IO) {
        var isLoginDone = false
        var isRoleFound = false

        val loginResultData = LoginResultData()

        for (countAttempt in 0 until maxAttempts) {

            loginStatus = "Tentativo di autenticazione ${countAttempt + 1} di 3\n"

            if (!isLoginDone) {
                val loginResponse = getAuth(credentials)

                if (loginResponse.code != 200) {
                    loginStatus += "Errore nell'autenticazione: ${loginResponse.code}."
                    continue
                }

                loginStatus += "Autenticazione avvenuta con successo!\n"
                println("Login Status: $loginStatus")

                loginResultData.guidToken = getAuthToken(loginResponse)
                guidToken = loginResultData.guidToken
                println("GUID Token: $guidToken")
                loginStatus += "Ricevuto token di accesso.\n"

//                if (credentials.courseID.toIntOrNull() != null) {
//                    if (!isStudentEnrolled.value) {
//                        runBlocking {
//                            navController.navigate(Screen.Enroll.route){
//                                launchSingleTop = true
//                            }
//                        }
//                    }
//                }

                isLoginDone = true
            }

            loginStatus += "Controllo del ruolo...\n"

            if (credentials.courseID.toIntOrNull() != null && !isRoleFound) {
                val testCheckRole = checkRole(credentials)

                if (testCheckRole.isSuccess) {
                    loginStatus += "Controllo del ruolo avvenuto con successo!\n"
                    println("testCheckRoleSuccess: true")
                    val roleResult = testCheckRole.getOrNull()
                    credentials.isCredentialFileExist = roleResult?.isCredentialsFound ?: false
                    loginResultData.swVersion = roleResult?.peerReviewRoleResponse
                    loginResultData.courseName = roleResult?.peerReviewRoleResponse?.class_name
                    isRoleFound = true
                } else break
            }
        }

        if (loginResultData.swVersion == null || !isRoleFound) {
            Result.failure(Exception("Errore nell'autenticazione"))
        } else {
            loginStatus += "Login completato!\n"
            delay(2000)
            saveData("loginInfo.json", Json.encodeToString(credentials))
            isLoggedIn = true
            courseId = credentials.courseID
            role = credentials.role

            Result.success(loginResultData)
        }
    }

    private fun getAuth(credentials: Credentials): Response {
        val loginData = LoginJsonData(
            email = credentials.email,
            password = credentials.password,
            website = 8
        )

        return ApiHelper.sendApiRequestPOST(loginData, ApiHelper.postLogin())
    }

    private fun getAuthToken(loginResponse: Response): String {
        val response = loginResponse.body!!.string()
        println(response)
        val token = Json.parseToJsonElement(response).jsonObject["token"]?.jsonPrimitive?.content
        val authToken = token?.removeSurrounding("{", "}") ?: throw Exception("Token not found")
        val cleanToken = UUID.fromString(authToken)
        println(cleanToken)
        return authToken
    }

    private fun checkRole(credentials: Credentials): Result<CheckRoleResult> {
        val result = CheckRoleResult(
            isCredentialsFound = credentials.isCredentialFileExist
        )

        val role = PeerReviewRoleJsonData(
            course_class_id = credentials.courseID.toInt(),
            role = credentials.role,
            website = 8
        )
        val roleResponse = getRole(role)

        if (roleResponse.isSuccessful) {
            val responseJson = roleResponse.body?.string()
                ?: return Result.failure(Exception("Errore nell'autorizzazione"))
            println(responseJson)
            result.peerReviewRoleResponse = Json.decodeFromString(responseJson)

            className = result.peerReviewRoleResponse?.class_name
                ?: return Result.failure(Exception("Errore nell'autorizzazione"))

            return Result.success(result)
        } else {
            loginStatus += "Errore nell'autorizzazione: ${roleResponse.code}.\n"
            println(roleResponse.code)
            return Result.failure(Exception("Errore nell'autorizzazione"))
        }
    }

    private fun getRole(roleData: PeerReviewRoleJsonData): Response {
        //println(roleData)
        return ApiHelper.sendApiRequestPOST(roleData, ApiHelper.postRole())
    }

}

*/


class LoginManager {

    companion object {
        var loginStatus by mutableStateOf("")
        var isLoggedIn by mutableStateOf(false)
        var isStudentEnrolled = mutableStateOf(false)

        var guidToken by mutableStateOf("")
        var courseId by mutableStateOf("")
        var role by mutableStateOf(PeerReviewRole.STUDENT)

        var className by mutableStateOf("")

        var maxAttempts = 3
    }

    suspend fun attemptLoginAsync(credentials: Credentials): Result<LoginResultData> =
        withContext(Dispatchers.IO) {
            var isLoginDone = false
            var isRoleFound = false

            val loginResultData = LoginResultData()

            for (countAttempt in 0 until maxAttempts) {
                loginStatus = "Tentativo di autenticazione ${countAttempt + 1} di 3\n"

                if (!isLoginDone) {
                    val loginResponse = getAuth(credentials)

                    if (loginResponse.code != 200) {
                        loginStatus += "Errore nell'autenticazione: ${loginResponse.code}."
                        continue
                    }

                    loginStatus += "Autenticazione avvenuta con successo!\n"
                    println("Login Status: $loginStatus")

                    loginResultData.guidToken = getAuthToken(loginResponse)
                    guidToken = loginResultData.guidToken
                    println("GUID Token: $guidToken")
                    loginStatus += "Ricevuto token di accesso.\n"

                    isLoginDone = true
                }

                loginStatus += "Controllo del ruolo...\n"

                if (credentials.courseID.toIntOrNull() != null && !isRoleFound) {
                    val testCheckRole = checkRole(credentials)

                    if (testCheckRole.isSuccess) {
                        loginStatus += "Controllo del ruolo avvenuto con successo!\n"
                        println("testCheckRoleSuccess: true")
                        val roleResult = testCheckRole.getOrNull()
                        credentials.isCredentialFileExist = roleResult?.isCredentialsFound ?: false
                        loginResultData.swVersion = roleResult?.peerReviewRoleResponse
                        loginResultData.courseName = roleResult?.peerReviewRoleResponse?.class_name
                        isRoleFound = true
                    } else break
                }
            }

            if (loginResultData.swVersion == null || !isRoleFound) {
                Result.failure(Exception("Errore nell'autenticazione"))
            } else {
                loginStatus += "Login completato!\n"
                delay(2000)
                saveData("loginInfo.json", Json.encodeToString(credentials))
                isLoggedIn = true
                courseId = credentials.courseID
                role = credentials.role

                Result.success(loginResultData)
            }
        }

    // Function to authenticate user
    private fun getAuth(credentials: Credentials): Response {
        val loginData = LoginJsonData(
            email = credentials.email,
            password = credentials.password,
            website = 8 // Change as needed for your website ID
        )

        // Send POST request using ApiHelper
        return ApiHelper.sendApiRequestPOST(loginData, ApiHelper.postLogin())
    }

    // Function to extract token from login response
    private fun getAuthToken(loginResponse: Response): String {
        val response = loginResponse.body!!.string()
        println(response)
        val token = Json.parseToJsonElement(response).jsonObject["token"]?.jsonPrimitive?.content
        val authToken = token?.removeSurrounding("{", "}") ?: throw Exception("Token not found")
        val cleanToken = UUID.fromString(authToken)
        println(cleanToken)
        return authToken
    }

    // Function to check role
    private fun checkRole(credentials: Credentials): Result<CheckRoleResult> {
        val result = CheckRoleResult(
            isCredentialsFound = credentials.isCredentialFileExist
        )

        val role = PeerReviewRoleJsonData(
            course_class_id = credentials.courseID.toInt(),
            role = credentials.role,
            website = 8 // Change as needed for your website ID
        )

        // Send POST request using ApiHelper
        val roleResponse = ApiHelper.sendApiRequestPOST(role, ApiHelper.postRole())

        if (roleResponse.isSuccessful) {
            val responseJson = roleResponse.body?.string()
                ?: return Result.failure(Exception("Errore nell'autorizzazione"))
            println(responseJson)
            result.peerReviewRoleResponse = Json.decodeFromString(responseJson)

            className = result.peerReviewRoleResponse?.class_name
                ?: return Result.failure(Exception("Errore nell'autorizzazione"))

            return Result.success(result)
        } else {
            loginStatus += "Errore nell'autorizzazione: ${roleResponse.code}.\n"
            println(roleResponse.code)
            return Result.failure(Exception("Errore nell'autorizzazione"))
        }
    }
}