package com.craftworks.peerreview.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.craftworks.peerreview.data.StudentLessonsTableData
import com.craftworks.peerreview.navigation.Screen
import com.craftworks.peerreview.ui.theme.peerReviewColorScheme
import org.jetbrains.compose.resources.Font
import peerreview.composeapp.generated.resources.Outfit_Light
import peerreview.composeapp.generated.resources.Res
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun StudentLesson(
    data: StudentLessonsTableData,
    navController: NavController
) {
    // ID / Title / Created At / First Deadline / Questions made / Second Deadline / Feedback made
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .wrapContentHeight()
            .heightIn(min = 32.dp, max = 64.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(peerReviewColorScheme.surfaceContainer)
            .clickable {
                navController.navigate(Screen.S_Grades.createRoute(data.id)) {
                    launchSingleTop = true
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = data.id.toString(),
            color = peerReviewColorScheme.onSurfaceVariant,
            fontFamily = FontFamily(Font(Res.font.Outfit_Light)),
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            modifier = Modifier.width(32.dp).wrapContentHeight(),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
        VerticalDivider(color = peerReviewColorScheme.surfaceBright)
        Text(
            text = data.title,
            color = peerReviewColorScheme.onSurfaceVariant,
            fontFamily = FontFamily(Font(Res.font.Outfit_Light)),
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            modifier = Modifier.weight(1f).wrapContentHeight().padding(horizontal = 6.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        VerticalDivider(color = peerReviewColorScheme.surfaceBright)
        Text(
            text = formatDateTime(data.created_at),
            color = peerReviewColorScheme.onSurfaceVariant,
            fontFamily = FontFamily(Font(Res.font.Outfit_Light)),
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            modifier = Modifier.weight(1f).wrapContentHeight().padding(horizontal = 6.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        VerticalDivider(color = peerReviewColorScheme.surfaceBright)
        Text(
            text = formatDateTime(data.first_deadline),
            color = peerReviewColorScheme.onSurfaceVariant,
            fontFamily = FontFamily(Font(Res.font.Outfit_Light)),
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            modifier = Modifier.weight(1f).wrapContentHeight().padding(horizontal = 6.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        VerticalDivider(color = peerReviewColorScheme.surfaceBright)
        Text(
            text = data.count_questions_made.toString(),
            color = if (data.count_questions_made == 0) peerReviewColorScheme.error else peerReviewColorScheme.onSurfaceVariant,
            fontFamily = FontFamily(Font(Res.font.Outfit_Light)),
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            modifier = Modifier.weight(1f).wrapContentHeight().padding(horizontal = 6.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
        VerticalDivider(color = peerReviewColorScheme.surfaceBright)
        Text(
            text = formatDateTime(data.second_deadline),
            color = peerReviewColorScheme.onSurfaceVariant,
            fontFamily = FontFamily(Font(Res.font.Outfit_Light)),
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            modifier = Modifier.weight(1f).wrapContentHeight().padding(horizontal = 6.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        VerticalDivider(color = peerReviewColorScheme.surfaceBright)
        Text(
            text = data.count_feedback_made.toString(),
            color = if (data.count_feedback_made == 0) peerReviewColorScheme.error else peerReviewColorScheme.onSurfaceVariant,
            fontFamily = FontFamily(Font(Res.font.Outfit_Light)),
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            modifier = Modifier.weight(1f).wrapContentHeight().padding(horizontal = 6.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

fun formatDateTime(dateTimeString: String): String {
    val dateTime = LocalDateTime.parse(dateTimeString)
    val formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy hh:mm a")

    return dateTime.format(formatter)
}