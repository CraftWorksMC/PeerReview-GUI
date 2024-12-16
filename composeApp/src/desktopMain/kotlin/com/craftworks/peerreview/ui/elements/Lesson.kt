package com.craftworks.peerreview.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.craftworks.peerreview.data.Lesson
import java.awt.Cursor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
@androidx.compose.desktop.ui.tooling.preview.Preview
fun StudentLessonPreview() {
    StudentLesson(
        Lesson(
            1,
            "Titolo della lezione",
            "Descrizione abbastanza corta della lezione svolta.",
            "Prof",
            1,
            "15 Dicembre 2024",
            "17 Dicembre 2024",
            listOf("Question 1", "Question 2", "Question 3")
        ),
        rememberNavController()
    )
}

@Composable
fun StudentLesson(
    lesson: Lesson,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row {
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f)
                    .height(128.dp)
            ) {
                // Lesson Title
                Text(
                    text = lesson.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                // Description
                Text(
                    text = lesson.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(Modifier.weight(1f))


                // Deadline
                Text(
                    text = "Scadenza: ${lesson.deadline}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                )
                // Created By
                Text(
                    text = "Creato da ${lesson.createdBy} il ${lesson.createdAt}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .width(192.dp)
                    .height(128.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // View Questions Button
                Button(
                    onClick = {
                        navController.navigate("questions/${lesson.id}")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerHoverIcon(
                            PointerIcon(
                                Cursor(Cursor.HAND_CURSOR)
                            )
                        ),
                    shape = RoundedCornerShape(12.dp),
                    enabled = lesson.questions.isNotEmpty()
                ) {
                    Text(
                        text = if (lesson.questions.isNotEmpty())
                            "Mostra ${lesson.questions.size} Domand${if (lesson.questions.size != 1) "e" else "a"}"
                        else
                            "Niente Domande"
                    )
                }
                // Give feedback Button
                Button(
                    onClick = {
                        navController.navigate("feedback/${lesson.id}")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerHoverIcon(
                            PointerIcon(
                                Cursor(Cursor.HAND_CURSOR)
                            )
                        ),
                    shape = RoundedCornerShape(12.dp),
                    enabled = lesson.questions.isNotEmpty()
                ) {
                    Text(
                        text = "Dai Feedback"
                    )
                }
            }
        }

    }
}

fun formatDateTime(dateTimeString: String): String {
    val dateTime = LocalDateTime.parse(dateTimeString)
    val formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy hh:mm a")

    return dateTime.format(formatter)
}