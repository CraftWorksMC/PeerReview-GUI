package com.craftworks.peerreview.ui.elements

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.craftworks.peerreview.data.PeerReviewQuestionData
import com.craftworks.peerreview.ui.theme.peerReviewColorScheme
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import peerreview.composeapp.generated.resources.Outfit_Bold
import peerreview.composeapp.generated.resources.Outfit_Light
import peerreview.composeapp.generated.resources.Res
import peerreview.composeapp.generated.resources.answer_isChatGpt

@Composable
fun StudentQuestionsToDo(
    data: PeerReviewQuestionData
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors()
            .copy(containerColor = peerReviewColorScheme.surfaceContainer),
    ) {
        Text(
            text = data.question_text,
            color = peerReviewColorScheme.onSurfaceVariant,
            fontFamily = FontFamily(Font(Res.font.Outfit_Bold)),
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                .padding(start = 12.dp, top = 12.dp),
            textAlign = TextAlign.Left
        )
        var answer by remember { mutableStateOf("") }

        OutlinedTextField(
            value = answer,
            onValueChange = { answer = it },
            label = { Text("Risposta") },
            minLines = 3,
            maxLines = 5,
            modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(12.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 12.dp, bottom = 12.dp)
        ) {
            var isChatGpt by remember { mutableStateOf(false) }

            Checkbox(
                checked = isChatGpt,
                onCheckedChange = { isChatGpt = it }
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
                onClick = { println("inviato") }
            ) {
                Text(
                    text = "Invia",
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