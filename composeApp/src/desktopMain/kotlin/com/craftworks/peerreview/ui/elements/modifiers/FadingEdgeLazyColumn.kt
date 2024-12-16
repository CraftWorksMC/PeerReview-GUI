import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.fadeGradient(
    listState: LazyListState,
    fadeHeight: Dp = 100.dp,
    backgroundColor: Color
): Modifier = drawWithContent {
    val topFadeEnd = fadeHeight.toPx()
    val bottomFadeStart = size.height - fadeHeight.toPx()

    drawContent()

    val firstVisibleItemIndex = listState.firstVisibleItemIndex
    val topFadeAlpha = if (firstVisibleItemIndex == 0) {
        (listState.firstVisibleItemScrollOffset / topFadeEnd).coerceIn(0f, 1f)
    } else {
        1f
    }

    val isAtBottom = !listState.canScrollForward
    val bottomFadeAlpha = if (isAtBottom) {
        val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
        val totalItemCount = listState.layoutInfo.totalItemsCount
        ((totalItemCount - lastVisibleItemIndex - 1) / 5f).coerceIn(0f, 1f)
    } else {
        1f
    }

    drawRect(
        brush = Brush.verticalGradient(
            colors = listOf(
                backgroundColor.copy(alpha = topFadeAlpha),
                Color.Transparent
            ),
            startY = 0f,
            endY = topFadeEnd
        ),
        size = Size(size.width, topFadeEnd)
    )

    drawRect(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.Transparent,
                backgroundColor.copy(alpha = bottomFadeAlpha)
            ),
            startY = bottomFadeStart,
            endY = size.height
        ),
        topLeft = Offset(0f, bottomFadeStart),
        size = Size(size.width, fadeHeight.toPx())
    )
}