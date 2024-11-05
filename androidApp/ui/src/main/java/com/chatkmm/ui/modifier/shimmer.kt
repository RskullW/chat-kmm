package com.chatkmm.ui.modifier

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.chatkmm.ui.B
import com.chatkmm.ui.MainTheme
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

@Composable
fun Modifier.shimmer(
    clipRadius: Dp = 0.dp,
    angle: Float = 0.0f,
    initialValue: Float = -500f,
    targetValue: Float = 1500f,
    durationMillis: Int = 2000
): Modifier {
    val transition = rememberInfiniteTransition()
    val shimmerX = transition.animateFloat(
        initialValue = initialValue,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val radians = Math.toRadians(angle.toDouble())
    val offsetX = cos(radians).toFloat() * 300f
    val offsetY = sin(radians).toFloat() * 300f

    return this
        .graphicsLayer(alpha = 0.99f)
        .clip(RoundedCornerShape(clipRadius))
        .background(
            brush = Brush.linearGradient(
                colors = listOf(B.colors().transparent, Color.White, B.colors().transparent),
                start = Offset(shimmerX.value, shimmerX.value * tan(radians).toFloat()),
                end = Offset(
                    shimmerX.value + offsetX,
                    shimmerX.value * tan(radians).toFloat() + offsetY
                )
            )
        )
}

@Composable
@Preview
internal fun Shimmer_Preview() {
    MainTheme {
        Box(
            modifier = Modifier
                .shimmer(
                    angle = 45.0f
                )
                .fillMaxWidth()
                .padding(48.dp)
        ) {

        }
    }
}