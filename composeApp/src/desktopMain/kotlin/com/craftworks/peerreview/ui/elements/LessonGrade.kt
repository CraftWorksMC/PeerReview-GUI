package com.craftworks.peerreview.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.craftworks.peerreview.data.PeerReviewAnswerData
import com.craftworks.peerreview.ui.theme.peerReviewColorScheme
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import peerreview.composeapp.generated.resources.Outfit_Bold
import peerreview.composeapp.generated.resources.Outfit_Light
import peerreview.composeapp.generated.resources.Res
import peerreview.composeapp.generated.resources.grades_created
import peerreview.composeapp.generated.resources.grades_feedback
import peerreview.composeapp.generated.resources.grades_grade
import peerreview.composeapp.generated.resources.grades_missing_elements

@Composable
fun StudentQuestionGrade(data: PeerReviewAnswerData) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(peerReviewColorScheme.surfaceContainer),
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Text(
                text = data.question_text,
                color = peerReviewColorScheme.onSurfaceVariant,
                fontFamily = FontFamily(Font(Res.font.Outfit_Bold)),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = data.answer_text,
                color = peerReviewColorScheme.onSurfaceVariant,
                fontFamily = FontFamily(Font(Res.font.Outfit_Light)),
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(Res.string.grades_feedback),
                color = peerReviewColorScheme.onSurfaceVariant,
                fontFamily = FontFamily(Font(Res.font.Outfit_Bold)),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            data.feedbacks.forEach { feedback ->
                Text(
                    text = feedback.feedback_text,
                    color = peerReviewColorScheme.onSurfaceVariant,
                    fontFamily = FontFamily(Font(Res.font.Outfit_Light)),
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(Res.string.grades_missing_elements),
                    color = peerReviewColorScheme.onSurfaceVariant,
                    fontFamily = FontFamily(Font(Res.font.Outfit_Bold)),
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = feedback.missing_elements,
                    color = peerReviewColorScheme.onSurfaceVariant,
                    fontFamily = FontFamily(Font(Res.font.Outfit_Light)),
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(Res.string.grades_grade) + feedback.grade,
                    color = if (feedback.grade < 6) peerReviewColorScheme.error else peerReviewColorScheme.onSurfaceVariant,
                    fontFamily = FontFamily(Font(Res.font.Outfit_Bold)),
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )

                if (feedback.created_at == null) return

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(Res.string.grades_created) + formatDateTime(feedback.created_at),
                    color = peerReviewColorScheme.onSurfaceVariant.copy(0.75f),
                    fontFamily = FontFamily(Font(Res.font.Outfit_Light)),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize
                )
            }
        }
    }
}