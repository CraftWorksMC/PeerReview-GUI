package com.craftworks.peerreview.ui

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.craftworks.peerreview.navigation.Screen
import com.craftworks.peerreview.ui.elements.EmptyViewItem
import com.craftworks.peerreview.ui.elements.ScreenHeader
import com.craftworks.peerreview.ui.elements.StudentFeedback
import com.craftworks.peerreview.ui.viewmodels.FeedbackViewModel
import fadeGradient
import org.jetbrains.compose.resources.stringResource
import peerreview.composeapp.generated.resources.Res
import peerreview.composeapp.generated.resources.empty_feedback_dark
import peerreview.composeapp.generated.resources.feedback_empty
import peerreview.composeapp.generated.resources.header_answer
import java.awt.Cursor

@Composable
fun StudentGiveFeedbackScreen(
    viewModel: FeedbackViewModel,
    navController: NavController
) {
    val studentQuestion by viewModel.studentFeedback.collectAsState()

    val columnState = rememberLazyListState()

    Column {
        Row {
            FilledIconButton(
                onClick = {
                    navController.navigate(Screen.S_Lessons.route) {
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .size(64.dp)
                    .padding(12.dp, 12.dp, 0.dp, 6.dp)
                    .pointerHoverIcon(PointerIcon(Cursor(Cursor.HAND_CURSOR))),
                shape = RoundedCornerShape(12.dp),
                colors = IconButtonDefaults.filledIconButtonColors()
                    .copy(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
            ) {
                Icon(
                    Icons.AutoMirrored.Rounded.ArrowBack,
                    "Back Arrow",
                    modifier = Modifier.size(48.dp)
                )
            }
            ScreenHeader(stringResource(Res.string.header_answer))
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .fadeGradient(columnState, 24.dp, MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(bottom = 6.dp),
            state = columnState,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (studentQuestion == null) {
                item {
                    EmptyViewItem(
                        Res.drawable.empty_feedback_dark,
                        Res.string.feedback_empty
                    )
                }
            } else
                item {
                    StudentFeedback(viewModel)
                }
        }


        VerticalScrollbar(
            modifier = Modifier.align(Alignment.End)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(columnState)
        )
    }
}