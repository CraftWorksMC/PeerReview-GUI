package com.craftworks.peerreview.ui.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.craftworks.peerreview.ui.theme.peerReviewColorScheme
import com.craftworks.peerreview.ui.viewmodels.FeedbackViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import peerreview.composeapp.generated.resources.Outfit_Bold
import peerreview.composeapp.generated.resources.Outfit_Light
import peerreview.composeapp.generated.resources.Res
import peerreview.composeapp.generated.resources.answer_isChatGpt
import peerreview.composeapp.generated.resources.answer_send
import peerreview.composeapp.generated.resources.feedback_feedback_text
import peerreview.composeapp.generated.resources.feedback_missing_elements
import java.awt.Cursor
import kotlin.math.abs

@Composable
fun StudentFeedback(
    viewModel: FeedbackViewModel
) {
    val data by viewModel.studentFeedback.collectAsState()

    var feedback by remember { mutableStateOf("") }
    var missingElements by remember { mutableStateOf("") }
    var grade by remember { mutableFloatStateOf(6f) }
    var isChatGpt by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    var hasSentFeedback by remember { mutableStateOf(false) }

    LaunchedEffect(data?.id) {
        println("Changed DATA: id ${data?.id}")
        hasSentFeedback = false
    }

    AnimatedVisibility(
        visible = !hasSentFeedback,
        enter = slideInHorizontally(
            initialOffsetX = { -it }, animationSpec = tween(durationMillis = 300)
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { it }, animationSpec = tween(durationMillis = 300)
        )
    ) {
        if (data == null) return@AnimatedVisibility
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors()
                .copy(containerColor = peerReviewColorScheme.surfaceContainer),
        ) {
            Text(
                text = data?.question!!,
                color = peerReviewColorScheme.onSurfaceVariant,
                fontFamily = FontFamily(Font(Res.font.Outfit_Bold)),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                modifier = Modifier.fillMaxWidth().wrapContentHeight()
                    .padding(start = 12.dp, top = 12.dp),
                textAlign = TextAlign.Left
            )
            Text(
                text = data?.answer_text!!,
                color = peerReviewColorScheme.onSurfaceVariant,
                fontFamily = FontFamily(Font(Res.font.Outfit_Light)),
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                modifier = Modifier.fillMaxWidth().wrapContentHeight()
                    .padding(start = 12.dp, top = 12.dp),
                textAlign = TextAlign.Left
            )

            OutlinedTextField(
                value = feedback,
                onValueChange = { feedback = it },
                label = {
                    Text(
                        text = stringResource(Res.string.feedback_feedback_text),
                        color = peerReviewColorScheme.onSurfaceVariant,
                        fontFamily = FontFamily(Font(Res.font.Outfit_Bold)),
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        textAlign = TextAlign.Left
                    )
                },
                maxLines = 5,
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(12.dp),
                enabled = !hasSentFeedback
            )

            OutlinedTextField(
                value = missingElements,
                onValueChange = { missingElements = it },
                label = {
                    Text(
                        text = stringResource(Res.string.feedback_missing_elements),
                        color = peerReviewColorScheme.onSurfaceVariant,
                        fontFamily = FontFamily(Font(Res.font.Outfit_Bold)),
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        textAlign = TextAlign.Left
                    )
                },
                maxLines = 5,
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(12.dp),
                enabled = !hasSentFeedback
            )

            val grades = listOf(
                4.0f, 4.5f, 5.0f, 5.5f, 6.0f, 6.5f, 7.0f, 7.5f, 8.0f
            )
            val minGrade = grades.first()
            val maxGrade = grades.last()

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "Voto: $grade",
                    color = peerReviewColorScheme.onSurfaceVariant,
                    fontFamily = FontFamily(Font(Res.font.Outfit_Bold)),
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.wrapContentHeight()
                        .padding(start = 0.dp, top = 0.dp, 12.dp),
                )

                // Discrete Slider
                Slider(
                    value = grade,
                    onValueChange = { newValue ->
                        grade = grades.minByOrNull { abs(it - newValue) } ?: grade
                    },
                    valueRange = minGrade..maxGrade,
                    steps = grades.size - 2,
                    onValueChangeFinished = { },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    grades.forEach { grade ->
                        Text(
                            text = grade.toString(),
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 12.dp, bottom = 12.dp)
            ) {
                Checkbox(
                    checked = isChatGpt,
                    onCheckedChange = { isChatGpt = it },
                    modifier = Modifier.pointerHoverIcon(PointerIcon(Cursor(Cursor.HAND_CURSOR)))
                )
                Text(
                    text = stringResource(Res.string.answer_isChatGpt),
                    color = peerReviewColorScheme.onSurfaceVariant,
                    fontFamily = FontFamily(Font(Res.font.Outfit_Light)),
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    modifier = Modifier.wrapContentHeight().padding(start = 6.dp),
                    textAlign = TextAlign.Left
                )

                Spacer(Modifier.weight(1f))

                Button(
                    enabled = feedback.isNotBlank() && missingElements.isNotBlank(),
                    onClick = {
                        coroutineScope.launch {
                            println("Sent the answer for question id ${data?.id}")
                            hasSentFeedback = true

                            viewModel.sendFeedback(
                                data?.id!!,
                                feedback,
                                missingElements,
                                grade,
                                isChatGpt
                            )

                            delay(600)

                            // Reset data to default
                            feedback = ""
                            missingElements = ""
                            grade = 6.0f
                            isChatGpt = false

                            runBlocking {
                                viewModel.getStudentFeedback()
                            }
                            println("Finished sending feedback!")
                        }
                    },
                    modifier = Modifier.pointerHoverIcon(PointerIcon(Cursor(Cursor.HAND_CURSOR)))
                ) {
                    Text(
                        text = stringResource(Res.string.answer_send),
                        color = peerReviewColorScheme.onPrimary,
                        fontFamily = FontFamily(Font(Res.font.Outfit_Light)),
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        modifier = Modifier.wrapContentHeight().padding(start = 6.dp),
                        textAlign = TextAlign.Left
                    )
                }
            }
        }
    }
}
