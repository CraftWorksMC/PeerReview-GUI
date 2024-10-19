package com.craftworks.peerreview.ui

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.craftworks.peerreview.ui.elements.EmptyViewItem
import com.craftworks.peerreview.ui.elements.ScreenHeader
import com.craftworks.peerreview.ui.elements.StudentFeedback
import com.craftworks.peerreview.ui.viewmodels.FeedbackViewModel
import org.jetbrains.compose.resources.stringResource
import peerreview.composeapp.generated.resources.Res
import peerreview.composeapp.generated.resources.answer_empty
import peerreview.composeapp.generated.resources.empty_questions_dark
import peerreview.composeapp.generated.resources.header_answer

@Composable
fun StudentGiveFeedbackScreen(
    viewModel: FeedbackViewModel = viewModel()
) {
    val studentQuestion by viewModel.studentFeedback.collectAsState()

    val columnState = rememberLazyListState()

    Column {
        ScreenHeader(stringResource(Res.string.header_answer))

        if (studentQuestion == null) {
            EmptyViewItem(
                Res.drawable.empty_questions_dark,
                Res.string.answer_empty
            )
        } else {
            StudentFeedback(studentQuestion!!, viewModel)
        }

        VerticalScrollbar(
            modifier = Modifier.align(Alignment.End)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(columnState)
        )
    }
}