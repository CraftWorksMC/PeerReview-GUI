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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.craftworks.peerreview.ui.elements.EmptyViewItem
import com.craftworks.peerreview.ui.elements.ScreenHeader
import com.craftworks.peerreview.ui.elements.StudentQuestionGrade
import com.craftworks.peerreview.ui.theme.peerReviewColorScheme
import com.craftworks.peerreview.ui.viewmodels.GradesViewmodel
import fadeGradient
import org.jetbrains.compose.resources.stringResource
import peerreview.composeapp.generated.resources.Res
import peerreview.composeapp.generated.resources.empty_grades_dark
import peerreview.composeapp.generated.resources.header_grades
import peerreview.composeapp.generated.resources.lessons_empty

@Composable
fun StudentGradeScreen(
    viewModel: GradesViewmodel = viewModel()
) {
    val studentGrades by viewModel.studentGrades.collectAsState()

    val columnState = rememberLazyListState()

    Column {
        Row {
            FilledIconButton(
                onClick = {},
                modifier = Modifier
                    .size(64.dp)
                    .padding(12.dp, 12.dp, 0.dp, 6.dp),
                shape = RoundedCornerShape(12.dp),
                colors = IconButtonDefaults.filledIconButtonColors()
                    .copy(
                        containerColor = peerReviewColorScheme.surfaceContainer,
                        contentColor = peerReviewColorScheme.onSurfaceVariant
                    )
            ) {
                Icon(
                    Icons.AutoMirrored.Rounded.ArrowBack,
                    "Back Arrow",
                    modifier = Modifier.size(48.dp)
                )
            }
            ScreenHeader(stringResource(Res.string.header_grades))
        }


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .fadeGradient(columnState, 24.dp),
            contentPadding = PaddingValues(bottom = 6.dp),
            state = columnState,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (studentGrades.isEmpty()) {
                item {
                    EmptyViewItem(
                        Res.drawable.empty_grades_dark,
                        Res.string.lessons_empty
                    )
                }
            }

            items(studentGrades) { question ->
                StudentQuestionGrade(question)
            }
        }

        VerticalScrollbar(
            modifier = Modifier.align(Alignment.End)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(columnState)
        )
    }
}