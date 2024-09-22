package com.craftworks.peerreview.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.craftworks.peerreview.api.ApiHelper
import com.craftworks.peerreview.login.LoginManager
import com.craftworks.peerreview.ui.theme.peerReviewColorScheme
import com.sunnychung.lib.android.composabletable.ux.Table
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import peerreview.composeapp.generated.resources.Outfit_Bold
import peerreview.composeapp.generated.resources.Res
import peerreview.composeapp.generated.resources.header_lessons
import peerreview.composeapp.generated.resources.header_login

@Composable
fun LessonsScreen(){
    LaunchedEffect(Unit){
        val lessonSummaryUrl = ApiHelper.getLessonsSummary(
            LoginManager.guidToken, LoginManager.courseId, LoginManager.role, 8
        )

        println(lessonSummaryUrl)

        val lessonSummaryData = ApiHelper.sendApiRequest(
            null,
            lessonSummaryUrl
        )

        println(lessonSummaryData)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(peerReviewColorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(12.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(peerReviewColorScheme.surfaceContainer)
        ){
            Text(
                text = stringResource(Res.string.header_lessons),
                modifier = Modifier.align(Alignment.Center),
                color = peerReviewColorScheme.onSurfaceVariant,
                fontFamily = FontFamily(Font(Res.font.Outfit_Bold)),
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
        }

        Table(
            rowCount = 7,
            columnCount = 10
        ) { rowIndex, columnIndex ->
            Text("($rowIndex, $columnIndex)")
        }
    }
}