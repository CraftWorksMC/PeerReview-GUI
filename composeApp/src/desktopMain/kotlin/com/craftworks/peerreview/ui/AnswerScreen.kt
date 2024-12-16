package com.craftworks.peerreview.ui

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.craftworks.peerreview.ui.elements.EmptyViewItem
import com.craftworks.peerreview.ui.elements.ScreenHeader
import com.craftworks.peerreview.ui.elements.StudentQuestionsToDo
import com.craftworks.peerreview.ui.viewmodels.AnswersViewModel
import fadeGradient
import org.jetbrains.compose.resources.stringResource
import peerreview.composeapp.generated.resources.Res
import peerreview.composeapp.generated.resources.answer_empty
import peerreview.composeapp.generated.resources.empty_questions_dark
import peerreview.composeapp.generated.resources.header_answer

@Composable
fun StudentAnswerScreen(
    viewModel: AnswersViewModel = viewModel()
) {
    val studentQuestions by viewModel.studentQuestions.collectAsState()

    val columnState = rememberLazyListState()

    Column {
        ScreenHeader(stringResource(Res.string.header_answer))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .fadeGradient(columnState, 24.dp, MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(bottom = 6.dp),
            state = columnState,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (studentQuestions.isEmpty()) {
                item {
                    EmptyViewItem(
                        Res.drawable.empty_questions_dark,
                        Res.string.answer_empty
                    )
                }
            }

            items(studentQuestions) { lessonQuestions ->
                for (question in lessonQuestions.lesson_questions)
                    StudentQuestionsToDo(question, viewModel)
            }
        }

        VerticalScrollbar(
            modifier = Modifier.align(Alignment.End)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(columnState)
        )
    }
}