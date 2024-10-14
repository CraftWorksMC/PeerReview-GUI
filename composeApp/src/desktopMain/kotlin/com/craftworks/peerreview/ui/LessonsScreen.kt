package com.craftworks.peerreview.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.craftworks.peerreview.login.LoginManager
import com.craftworks.peerreview.ui.elements.ScreenHeader
import com.craftworks.peerreview.ui.elements.StudentLesson
import com.craftworks.peerreview.ui.theme.peerReviewColorScheme
import com.craftworks.peerreview.ui.viewmodels.LessonsViewmodel
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import peerreview.composeapp.generated.resources.Outfit_Bold
import peerreview.composeapp.generated.resources.Res
import peerreview.composeapp.generated.resources.header_lessons
import peerreview.composeapp.generated.resources.lessons_created
import peerreview.composeapp.generated.resources.lessons_feedback
import peerreview.composeapp.generated.resources.lessons_first_deadline
import peerreview.composeapp.generated.resources.lessons_questions
import peerreview.composeapp.generated.resources.lessons_second_deadline
import peerreview.composeapp.generated.resources.lessons_title

@Composable
fun StudentLessonsScreen(
    viewModel: LessonsViewmodel = viewModel()
) {
    val studentLessons by viewModel.studentLessons.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(peerReviewColorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenHeader(stringResource(Res.string.header_lessons) + LoginManager.className)

        // ID / Title / Created At / First Deadline / Questions made / Second Deadline / Feedback made
        Row(
            modifier = Modifier.height(32.dp).padding(horizontal = 12.dp, vertical = 6.dp),
        ) {
            Text(
                text = "ID",
                color = peerReviewColorScheme.onSurfaceVariant,
                fontFamily = FontFamily(Font(Res.font.Outfit_Bold)),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                modifier = Modifier.width(32.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            VerticalDivider(color = peerReviewColorScheme.surfaceContainer)
            Text(
                text = stringResource(Res.string.lessons_title),
                color = peerReviewColorScheme.onSurfaceVariant,
                fontFamily = FontFamily(Font(Res.font.Outfit_Bold)),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                modifier = Modifier.weight(1f).padding(horizontal = 6.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            VerticalDivider(color = peerReviewColorScheme.surfaceContainer)
            Text(
                text = stringResource(Res.string.lessons_created),
                color = peerReviewColorScheme.onSurfaceVariant,
                fontFamily = FontFamily(Font(Res.font.Outfit_Bold)),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                modifier = Modifier.weight(1f).padding(horizontal = 6.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            VerticalDivider(color = peerReviewColorScheme.surfaceContainer)
            Text(
                text = stringResource(Res.string.lessons_first_deadline),
                color = peerReviewColorScheme.onSurfaceVariant,
                fontFamily = FontFamily(Font(Res.font.Outfit_Bold)),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                modifier = Modifier.weight(1f).padding(horizontal = 6.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            VerticalDivider(color = peerReviewColorScheme.surfaceContainer)
            Text(
                text = stringResource(Res.string.lessons_questions),
                color = peerReviewColorScheme.onSurfaceVariant,
                fontFamily = FontFamily(Font(Res.font.Outfit_Bold)),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                modifier = Modifier.weight(1f).padding(horizontal = 6.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            VerticalDivider(color = peerReviewColorScheme.surfaceContainer)
            Text(
                text = stringResource(Res.string.lessons_second_deadline),
                color = peerReviewColorScheme.onSurfaceVariant,
                fontFamily = FontFamily(Font(Res.font.Outfit_Bold)),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                modifier = Modifier.weight(1f).padding(horizontal = 6.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            VerticalDivider(color = peerReviewColorScheme.surfaceContainer)
            Text(
                text = stringResource(Res.string.lessons_feedback),
                color = peerReviewColorScheme.onSurfaceVariant,
                fontFamily = FontFamily(Font(Res.font.Outfit_Bold)),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                modifier = Modifier.weight(1f).padding(horizontal = 6.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }

        studentLessons.forEach { lesson ->
            StudentLesson(lesson)
        }
    }
}