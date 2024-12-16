package com.craftworks.peerreview.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.craftworks.peerreview.data.legacy.PeerReviewAnswerData
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import peerreview.composeapp.generated.resources.AI_Icon_Color
import peerreview.composeapp.generated.resources.Outfit_Bold
import peerreview.composeapp.generated.resources.Outfit_Light
import peerreview.composeapp.generated.resources.Res
import peerreview.composeapp.generated.resources.grades_created
import peerreview.composeapp.generated.resources.grades_feedback
import peerreview.composeapp.generated.resources.grades_grade
import peerreview.composeapp.generated.resources.grades_missing_elements

@Composable
fun StudentQuestionGrade(data: PeerReviewAnswerData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors()
            .copy(containerColor = MaterialTheme.colorScheme.surfaceContainer),
    ) {
        Text(
            text = data.question_text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontFamily = FontFamily(Font(Res.font.Outfit_Bold)),
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                .padding(start = 12.dp, top = 12.dp),
            textAlign = TextAlign.Left
        )

        Text(
            text = data.answer_text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontFamily = FontFamily(Font(Res.font.Outfit_Light)),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            modifier = Modifier.padding(12.dp)
        )

        data.feedbacks.forEach { feedback ->
            Card(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                shape = RoundedCornerShape(6.dp),
                colors = CardDefaults.cardColors()
                    .copy(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
            ) {
                Text(
                    text = stringResource(Res.string.grades_feedback),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = FontFamily(Font(Res.font.Outfit_Bold)),
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                        .padding(start = 12.dp, top = 12.dp),
                    textAlign = TextAlign.Left
                )

                Text(
                    text = feedback.feedback_text,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = FontFamily(Font(Res.font.Outfit_Light)),
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    modifier = Modifier.padding(start = 12.dp)
                )

                Text(
                    text = stringResource(Res.string.grades_missing_elements),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = FontFamily(Font(Res.font.Outfit_Bold)),
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                        .padding(start = 12.dp, top = 12.dp),
                    textAlign = TextAlign.Left
                )

                Text(
                    text = feedback.missing_elements,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = FontFamily(Font(Res.font.Outfit_Light)),
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    modifier = Modifier.padding(start = 12.dp, bottom = 12.dp)
                )

                Box(
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    // Grade
                    Text(
                        text = stringResource(Res.string.grades_grade) + feedback.grade,
                        color = if (feedback.grade < 6) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontFamily = FontFamily(Font(Res.font.Outfit_Bold)),
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        modifier = Modifier.align(Alignment.TopStart)
                            .padding(start = 12.dp)
                    )

                    // Creation date of feedback
                    if (feedback.created_at != null) {
                        Text(
                            text = stringResource(Res.string.grades_created) + formatDateTime(
                                feedback.created_at
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.75f),
                            fontFamily = FontFamily(Font(Res.font.Outfit_Light)),
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            modifier = Modifier.align(Alignment.BottomStart)
                                .padding(start = 12.dp, bottom = 6.dp),
                            textAlign = TextAlign.Left
                        )
                    }

                    // AI Icon
                    if (data.is_chat_gpt == 1) {
                        Image(
                            painter = painterResource(Res.drawable.AI_Icon_Color),
                            contentScale = ContentScale.FillHeight,
                            contentDescription = "AI",
                            modifier = Modifier
                                .size(48.dp)
                                .align(Alignment.CenterEnd)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))
    }
}