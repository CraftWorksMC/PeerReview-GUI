package com.craftworks.peerreview.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.craftworks.peerreview.ui.elements.ScreenHeader
import com.craftworks.peerreview.ui.elements.StudentQuestionGrade
import com.craftworks.peerreview.ui.viewmodels.GradesViewmodel
import org.jetbrains.compose.resources.stringResource
import peerreview.composeapp.generated.resources.Res
import peerreview.composeapp.generated.resources.header_grades

@Composable
fun StudentGradeScreen(
    viewModel: GradesViewmodel = viewModel()
) {
    val studentGrades by viewModel.studentGrades.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(bottom = 6.dp)
    ) {
        item {
            ScreenHeader(stringResource(Res.string.header_grades))
        }

        items(studentGrades) { question ->
            StudentQuestionGrade(question)
        }
    }
}