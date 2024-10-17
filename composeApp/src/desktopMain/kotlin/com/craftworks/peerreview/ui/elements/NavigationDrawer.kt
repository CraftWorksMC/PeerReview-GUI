package com.craftworks.peerreview.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.craftworks.peerreview.navigation.Screen
import com.craftworks.peerreview.ui.theme.peerReviewColorScheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import peerreview.composeapp.generated.resources.BaobabLogoNew
import peerreview.composeapp.generated.resources.Res
import peerreview.composeapp.generated.resources.nav_answer
import peerreview.composeapp.generated.resources.nav_feedback
import peerreview.composeapp.generated.resources.nav_lessons

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
            onClick = { onItemClick(Screen.S_Lessons.route) },
            colors = navigationDrawerColors,
            modifier = Modifier.padding(vertical = 6.dp).height(48.dp),
            shape = RoundedCornerShape(12.dp),
        )
        NavigationDrawerItem(
            label = { Text(stringResource(Res.string.nav_answer)) },
            selected = false,
            onClick = { onItemClick(Screen.S_Answer.route) },
            colors = navigationDrawerColors,
            modifier = Modifier.padding(vertical = 6.dp).height(48.dp),
            shape = RoundedCornerShape(12.dp),
        )
        NavigationDrawerItem(
            label = { Text(stringResource(Res.string.nav_feedback)) },
            selected = false,
            onClick = { onItemClick(Screen.S_Lessons.route) },
            colors = navigationDrawerColors,
            modifier = Modifier.padding(vertical = 6.dp).height(48.dp),
            shape = RoundedCornerShape(12.dp),
        )
//        NavigationDrawerItem(
//            label = { Text(stringResource(Res.string.nav_grades)) },
//            selected = false,
//            onClick = { onItemClick(Screen.S_Grades.route) },
//            colors = navigationDrawerColors,
//            modifier = Modifier.padding(vertical = 6.dp).height(48.dp),
//            shape = RoundedCornerShape(12.dp),
//        )
    }
}