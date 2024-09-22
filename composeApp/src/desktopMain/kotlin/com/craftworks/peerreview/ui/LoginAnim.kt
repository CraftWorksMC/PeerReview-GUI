import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.sqrt

@Composable
fun LoginScreenAnim() {

    println("RECOMPOSE")
    var showAnimation by remember { mutableStateOf(false) }
    var buttonPosition by remember { mutableStateOf(Offset.Zero) }
    var screenSize by remember { mutableStateOf(Offset.Zero) }
    var buttonSize by remember { mutableStateOf(IntSize.Zero) }

    val animationProgress by animateFloatAsState(
        targetValue = if (showAnimation) 1f else 0f,
        animationSpec = tween(1600, easing = FastOutSlowInEasing)
    )

    Box(modifier = Modifier.fillMaxSize().onGloballyPositioned { coordinates ->
        screenSize = Offset(coordinates.size.width.toFloat(), coordinates.size.height.toFloat())
    }) {
        // Original content
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Good Morning!", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { showAnimation = true },
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    buttonPosition = coordinates.localToWindow(Offset.Zero)
                    buttonSize = coordinates.size // Capture the button's size
                }
            ) {
                Text("Login")
            }
        }

        // Circular reveal animation
//        Canvas(modifier = Modifier.fillMaxSize()) {
//            val radius = sqrt(screenSize.x * screenSize.x + screenSize.y * screenSize.y) * animationProgress
//
//            // Calculate the center of the button for the animation starting point
//            val buttonCenter = buttonPosition + Offset(buttonSize.width / 2f, buttonSize.height / 2f)
//
//            drawCircle(
//                color = Color.Magenta,
//                center = buttonCenter,
//                radius = radius
//            )
//        }

        // New content (visible when animation completes)
//        AnimatedVisibility(
//            visible = animationProgress > 0.75f,
//            enter = fadeIn(animationSpec = tween(600))
//        ) {
//
//        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("✓", style = MaterialTheme.typography.h2, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Username", color = Color.White) },
                modifier = Modifier.width(280.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Password", color = Color.White) },
                modifier = Modifier.width(280.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* Handle sign in */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Text("Sign In", color = Color.Magenta)
            }
        }
    }
}
