package com.craftworks.peerreview

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.navigation.compose.rememberNavController
import com.craftworks.peerreview.login.LoginManager
import com.craftworks.peerreview.navigation.SetupNavGraph
import com.craftworks.peerreview.ui.LoginScreen
import com.craftworks.peerreview.ui.elements.DrawerContent
import com.craftworks.peerreview.ui.theme.peerReviewColorScheme
import java.awt.Dimension


// Application entry point. Create window with minimum dimentions of 800x600.
// Then we setup the navgraph and navigate to the login screen as first destination.

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "PeerReview",
    ) {
        window.minimumSize = Dimension(800, 600)

        MaterialTheme(
            colorScheme = peerReviewColorScheme
        ) {
            AnimatedContent(
                targetState = LoginManager.isLoggedIn,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith
                            fadeOut(animationSpec = tween(300))
                }
            ) { loggedIn ->
                if (loggedIn) {
                    val navController = rememberNavController()

                    PermanentNavigationDrawer(
                        drawerContent = {
                            PermanentDrawerSheet(
                                Modifier.width(192.dp),
                                drawerContainerColor = peerReviewColorScheme.surfaceContainer,
                            ) {
                                DrawerContent(
                                    onItemClick = { route ->
                                        navController.navigate(route) {
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                            .background(peerReviewColorScheme.surfaceContainer),
                    ) {
                        SetupNavGraph(navController)
                    }
                } else {
                    LoginScreen()
                }
            }
        }
    }
}