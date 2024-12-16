package com.craftworks.peerreview.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.scale
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.craftworks.peerreview.api.ApiClient
import com.craftworks.peerreview.api.ApiRepository
import com.craftworks.peerreview.data.AppTheme
import com.craftworks.peerreview.data.PeerReviewRole
import com.craftworks.peerreview.data.Theme
import com.craftworks.peerreview.data.User
import com.craftworks.peerreview.loadData
import com.craftworks.peerreview.ui.elements.RegisterLoginToggle
import com.craftworks.peerreview.ui.elements.ScreenHeader
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.stringResource
import peerreview.composeapp.generated.resources.Res
import peerreview.composeapp.generated.resources.header_login
import peerreview.composeapp.generated.resources.header_register
import java.awt.Cursor

@Composable
fun LoginScreen(onLoginSuccess: (user: User) -> Unit) {
    var name: String by remember { mutableStateOf("") }

    var email: String by remember { mutableStateOf("") }
    val emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$".toRegex()

    var password: String by remember { mutableStateOf("") }
    var passwordVisible: Boolean by remember { mutableStateOf(false) }

    var isLoading: Boolean by remember { mutableStateOf(false) }
    var isRegister: Boolean by remember { mutableStateOf(false) }
    var isLoginError: Boolean by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    var buttonPosition by remember { mutableStateOf(Offset.Zero) }
    var buttonSize by remember { mutableStateOf(IntSize.Zero) }

    // Get saved credentials at launch
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            println("Getting saved credentials")
            try {
                val loadedCredentials = loadData("loginInfo.json")
                println(loadedCredentials)
                if (loadedCredentials != null) {
                    val credentials = Json.decodeFromString<List<String>>(loadedCredentials)
                    email = credentials[0]
                    password = credentials[1]
                }
            } catch (e: SerializationException) {
                // If JSON deserialization fails, use default input
                println("Failed to load data: ${e.message}, using default input")
            } catch (e: Exception) {
                // Catch any other exceptions
                println("An error occurred: ${e.message}, using default input")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenHeader(if (isRegister)
            stringResource(Res.string.header_register)
        else
            stringResource(Res.string.header_login)
        )

        // Register name TextField
        AnimatedVisibility(isRegister) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome") },
                modifier = Modifier
                    .width(320.dp)
                    .padding(vertical = 8.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                shape = RoundedCornerShape(12.dp)
            )
        }

        // Email TextField
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .width(320.dp)
                .padding(vertical = 8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(12.dp),
            isError = isLoginError
        )

        // Password TextField
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .width(320.dp)
                .padding(vertical = 8.dp),
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = {
                val icon =
                    if (passwordVisible) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff
                IconButton(
                    onClick = { passwordVisible = !passwordVisible },
                    modifier = Modifier.pointerHoverIcon(PointerIcon(Cursor(Cursor.HAND_CURSOR)))
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = if (passwordVisible) "Nascondi passowrd" else "Mostra password"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = if (isRegister) ImeAction.Next else ImeAction.Done
            ),
            shape = RoundedCornerShape(12.dp),
            isError = isLoginError
        )

        // Login Button
        Button(
            onClick = {
                coroutineScope.launch {
                    isLoading = true
                    val request: Result<HttpResponse>

                    if (isRegister) {
                        request = ApiRepository().postRegister(
                            User(
                                name = name,
                                email = email,
                                password = password,
                                classId = -1,
                                role = PeerReviewRole.STUDENT
                            )
                        )
                    }
                    else {
                        ApiClient.setCredentials(email, password)
                        request = ApiRepository().getAuth()
                    }

                    request.onSuccess { response ->
                        val user = Json.decodeFromString<User>(response.bodyAsText())
                        onLoginSuccess(user)
                    }.onFailure { error ->
                        isLoginError = true
                        println("Error: ${error.message}")
                    }

                    isLoading = false
                }
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .width(320.dp)
                .onGloballyPositioned { coordinates ->
                    buttonPosition = coordinates.localToWindow(Offset.Zero)
                    buttonSize = coordinates.size
                }
                .pointerHoverIcon(
                    PointerIcon(
                        Cursor(
                            if (email.matches(emailPattern) && password.length >= 2 && (if (isRegister) name.isNotBlank() else true))
                                Cursor.HAND_CURSOR
                            else
                                Cursor.DEFAULT_CURSOR
                        )
                    )
                ),
            shape = RoundedCornerShape(12.dp),
            enabled = email.matches(emailPattern) && password.length >= 2 && (if (isRegister) name.isNotBlank() else true)
        ) {
            AnimatedContent(
                targetState = isLoading
            ) {
                if (it) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 4.dp,
                        strokeCap = StrokeCap.Round
                    )
                } else {
                    Text(
                        if (isRegister)
                            stringResource(Res.string.header_register)
                        else
                            stringResource(Res.string.header_login)
                    )
                }
            }

        }

        // Register button
        RegisterLoginToggle(
            onClick = { isRegister = !isRegister },
            isRegister = isRegister
        )
    }

    Box(
        modifier = Modifier.fillMaxSize().padding(12.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        FloatingActionButton(
            onClick = {
                AppTheme.set(
                    if (AppTheme.current == Theme.Dark)
                        Theme.Light
                    else
                        Theme.Dark
                )
            },
            modifier = Modifier.pointerHoverIcon(
                PointerIcon(Cursor(Cursor.HAND_CURSOR))
            )
        ) {
            Crossfade(targetState = AppTheme.current) { theme ->
                val targetScale = if (theme == Theme.Light) 0.9f else 1.2f
                val scale = animateFloatAsState(targetValue = targetScale)
                Icon(
                    imageVector = if (theme == Theme.Light) Icons.Rounded.DarkMode else Icons.Rounded.LightMode,
                    contentDescription = "Cambia tema",
                    modifier = Modifier.graphicsLayer {
                        scaleX = scale.value
                        scaleY = scale.value
                    }
                )
            }
        }
    }
}