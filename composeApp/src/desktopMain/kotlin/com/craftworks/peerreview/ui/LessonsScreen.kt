package com.craftworks.peerreview.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.craftworks.peerreview.ui.elements.EmptyViewItem
import com.craftworks.peerreview.ui.elements.ScreenHeader
import com.craftworks.peerreview.ui.elements.StudentLesson
import com.craftworks.peerreview.ui.viewmodels.LessonsViewmodel
import fadeGradient
import org.jetbrains.compose.resources.stringResource
import peerreview.composeapp.generated.resources.Res
import peerreview.composeapp.generated.resources.empty_lessons_dark
import peerreview.composeapp.generated.resources.grades_empty
import peerreview.composeapp.generated.resources.header_lessons

@Composable
fun StudentLessonsScreen(
    viewModel: LessonsViewmodel = viewModel(),
    navController: NavController
) {
    val studentLessons by viewModel.studentLessons.collectAsState()

    val columnState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenHeader(stringResource(Res.string.header_lessons))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .fadeGradient(columnState, 24.dp, MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(bottom = 6.dp),
            state = columnState,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (studentLessons.isEmpty()) {
                item {
                    EmptyViewItem(
                        Res.drawable.empty_lessons_dark,
                        Res.string.grades_empty
                    )
                }
            }

            items(studentLessons) { lesson ->
                StudentLesson(lesson, navController)
            }
        }
    }
}