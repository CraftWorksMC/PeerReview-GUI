package com.craftworks.peerreview

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.navigation.compose.rememberNavController
import com.craftworks.peerreview.login.LoginManager
import com.craftworks.peerreview.navigation.Screen
import com.craftworks.peerreview.navigation.SetupNavGraph
import com.craftworks.peerreview.ui.LoginScreen
import com.craftworks.peerreview.ui.theme.peerReviewColorScheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import peerreview.composeapp.generated.resources.BaobabLogoNew
import peerreview.composeapp.generated.resources.Res
import peerreview.composeapp.generated.resources.nav_answer
import peerreview.composeapp.generated.resources.nav_feedback
import peerreview.composeapp.generated.resources.nav_grades
import peerreview.composeapp.generated.resources.nav_lessons
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

@Composable
fun DrawerContent(onItemClick: (String) -> Unit) {
    val navigationDrawerColors = NavigationDrawerItemDefaults.colors(
        selectedContainerColor = peerReviewColorScheme.primary,
        unselectedContainerColor = Color.Transparent,
    )

    Column(
        modifier = Modifier.padding(12.dp)
    ) {
        // Baobab.school Logo
        Image(
            painter = painterResource(Res.drawable.BaobabLogoNew),
            contentScale = ContentScale.FillWidth,
            contentDescription = "Baobab.school Logo",
            modifier = Modifier.fillMaxWidth().height(48.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        NavigationDrawerItem(
            label = { Text(stringResource(Res.string.nav_lessons)) },
            selected = false,
            onClick = { onItemClick(Screen.Lessons.route) },
            colors = navigationDrawerColors,
            modifier = Modifier.padding(vertical = 6.dp).height(48.dp),
            shape = RoundedCornerShape(12.dp),
        )
        NavigationDrawerItem(
            label = { Text(stringResource(Res.string.nav_answer)) },
            selected = false,
            onClick = { onItemClick(Screen.Lessons.route) },
            colors = navigationDrawerColors,
            modifier = Modifier.padding(vertical = 6.dp).height(48.dp),
            shape = RoundedCornerShape(12.dp),
        )
        NavigationDrawerItem(
            label = { Text(stringResource(Res.string.nav_feedback)) },
            selected = false,
            onClick = { onItemClick(Screen.Lessons.route) },
            colors = navigationDrawerColors,
            modifier = Modifier.padding(vertical = 6.dp).height(48.dp),
            shape = RoundedCornerShape(12.dp),
        )
        NavigationDrawerItem(
            label = { Text(stringResource(Res.string.nav_grades)) },
            selected = false,
            onClick = { onItemClick(Screen.Lessons.route) },
            colors = navigationDrawerColors,
            modifier = Modifier.padding(vertical = 6.dp).height(48.dp),
            shape = RoundedCornerShape(12.dp),
        )
    }
}