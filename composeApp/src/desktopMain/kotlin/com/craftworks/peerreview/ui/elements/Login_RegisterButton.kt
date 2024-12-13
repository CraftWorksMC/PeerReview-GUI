package com.craftworks.peerreview.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import peerreview.composeapp.generated.resources.Res
import peerreview.composeapp.generated.resources.login_text
import peerreview.composeapp.generated.resources.register_text
import java.awt.Cursor
import kotlin.text.split

@Composable
fun RegisterLoginToggle(
    onClick: () -> Unit,
    isRegister: Boolean // State to track registration status
) {
    val text = if (isRegister) {
        stringResource(Res.string.login_text)
    } else {
        stringResource(Res.string.register_text)
    }

    val (textPart, clickablePart) = remember(text) {
        // Split the text into two parts
        val parts = text.split("? ")
        parts[0] + "? " to parts[1]
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = textPart,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = clickablePart,
            modifier = Modifier
                .clickable {
                    onClick()
                }
                .pointerHoverIcon(
                    PointerIcon(
                        Cursor(Cursor.HAND_CURSOR)
                    )
                ),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}