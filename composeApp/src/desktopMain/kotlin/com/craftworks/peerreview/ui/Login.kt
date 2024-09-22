package com.craftworks.peerreview.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.craftworks.peerreview.data.Credentials
import com.craftworks.peerreview.data.PeerReviewRole
import com.craftworks.peerreview.loadData
import com.craftworks.peerreview.login.LoginManager
import com.craftworks.peerreview.ui.elements.modifiers.circularReveal
import com.craftworks.peerreview.ui.theme.peerReviewColorScheme
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import peerreview.composeapp.generated.resources.Outfit_Bold
import peerreview.composeapp.generated.resources.Res
import peerreview.composeapp.generated.resources.header_login
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Preview
@Composable
fun LoginScreen() {
    var email: String by remember { mutableStateOf("") }
    var password: String by remember { mutableStateOf("") }
    var passwordVisible: Boolean by remember { mutableStateOf(false) }
    var courseID: String by remember { mutableStateOf("") }
    var peerReviewRole: PeerReviewRole by remember { mutableStateOf(PeerReviewRole.STUDENT) }

    val coroutineScope = rememberCoroutineScope()

    var credentials: Credentials

    var buttonPosition by remember { mutableStateOf(Offset.Zero) }
    var buttonSize by remember { mutableStateOf(IntSize.Zero) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            println("Getting saved credentials...")
            try {
                val loadedCredentials = loadData("loginInfo.json")
                println(loadedCredentials)
                if (loadedCredentials != null) {

                    credentials = Json.decodeFromString<Credentials>(loadedCredentials)

                    if (credentials.email.isBlank() ||
                        credentials.password.isBlank() ||
                        credentials.courseID.isBlank()) return@launch

                    email = credentials.email
                    password = credentials.password
                    courseID = credentials.courseID
                    peerReviewRole = credentials.role

                    LoginManager().attemptLoginAsync(credentials)
                }
            } catch (e: SerializationException) {
                // If JSON deserialization fails, use default input
                println("Failed to load data: ${e.message}, using default input")
            } catch (e: Exception) {
                // Catch any other exceptions
                println("An error occurred: ${e.message}, using default input")
            }

            credentials = Credentials(
                email, password, courseID, peerReviewRole
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(peerReviewColorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(12.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(peerReviewColorScheme.surfaceContainer)
        ){
            Text(
                text = stringResource(Res.string.header_login),
                modifier = Modifier.align(Alignment.Center),
                color = peerReviewColorScheme.onSurfaceVariant,
                fontFamily = FontFamily(Font(Res.font.Outfit_Bold)),
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
        }

        // Email TextField
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Username") },
            modifier = Modifier
                .width(320.dp)
                .padding(vertical = 8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            shape = RoundedCornerShape(12.dp)
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
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = if (passwordVisible) "Nascondi passowrd" else "Mostra password"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            shape = RoundedCornerShape(12.dp)
        )

        // Class ID TextField
        OutlinedTextField(
            value = courseID,
            onValueChange = { courseID = it },
            label = { Text("ID Classe") },
            modifier = Modifier
                .width(320.dp)
                .padding(vertical = 8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            shape = RoundedCornerShape(12.dp)
        )

        // Role Choice
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.width(320.dp).padding(vertical = 8.dp)
        ) {
            SegmentedButton(
                onClick = { peerReviewRole = PeerReviewRole.STUDENT },
                label = { Text("Studente") },
                icon = {},
                shape = SegmentedButtonDefaults.itemShape(0, 2),
                selected = peerReviewRole == PeerReviewRole.STUDENT
            )
            SegmentedButton(
                onClick = { peerReviewRole = PeerReviewRole.TEACHER },
                label = { Text("Docente") },
                icon = {},
                shape = SegmentedButtonDefaults.itemShape(1, 2),
                selected = peerReviewRole == PeerReviewRole.TEACHER
            )
        }

        // Login Button
        Button(
            onClick = {
                credentials = Credentials(
                    email, password, courseID, peerReviewRole
                )
                println("Logging in with credentials: $credentials")
                coroutineScope.launch {
                    LoginManager().attemptLoginAsync(credentials)
                }
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .width(320.dp)
                .onGloballyPositioned { coordinates ->
                    buttonPosition = coordinates.localToWindow(Offset.Zero)
                    buttonSize = coordinates.size
                },
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Accedi")
        }
    }

    Column(
        Modifier.fillMaxSize()
            .circularReveal(
                isVisible = LoginManager.loginStatus.isNotBlank(),
                revealFrom = Offset(buttonPosition.x + (buttonSize.width / 2), buttonPosition.y + (buttonSize.height / 2)),
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
            .background(peerReviewColorScheme.surfaceContainer)
            .then(
                if (LoginManager.loginStatus.isNotBlank())
                    Modifier.pointerInput(Unit) { detectTapGestures { } }
                else
                    Modifier
            )

        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            strokeCap = StrokeCap.Round,
            color = peerReviewColorScheme.onBackground
        )

        Text(
            text = LoginManager.loginStatus,
            color = peerReviewColorScheme.onBackground
        )

        // Login Button
        Button(
            onClick = {
                LoginManager.loginStatus = ""
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .width(320.dp)
                .animateContentSize(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Riprova")
        }
    }
}