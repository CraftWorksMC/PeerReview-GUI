package com.craftworks.peerreview.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.craftworks.peerreview.ui.theme.peerReviewColorScheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import peerreview.composeapp.generated.resources.Outfit_Bold
import peerreview.composeapp.generated.resources.Res

@Composable
fun EmptyViewItem(
    icon: DrawableResource,
    text: StringResource
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(12.dp, top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(192.dp)
        )

        Text(
            text = stringResource(text),
            color = peerReviewColorScheme.onSurfaceVariant,
            fontFamily = FontFamily(Font(Res.font.Outfit_Bold)),
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            modifier = Modifier.padding(12.dp)
        )
    }
}