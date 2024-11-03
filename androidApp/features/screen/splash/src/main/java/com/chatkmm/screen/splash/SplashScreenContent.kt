package com.chatkmm.screen.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chatkmm.data.utils.globalApplicationContext
import com.chatkmm.data.utils.localize
import com.chatkmm.resources.MultiplatformResource
import com.chatkmm.ui.B
import com.chatkmm.ui.MainTheme
import com.chatkmm.ui.TypewriteText
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun SplashScreenContent() {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!isVisible) {
            delay(TimeUnit.SECONDS.toMillis(2))
            isVisible = true
        }
    }

    Box(
        modifier = Modifier
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(B.colors().black, B.colors().primary),
                )
            )
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            ScalingRotatingLoader()
        }

        if (isVisible) {
            TypewriteText(
                text = MultiplatformResource.strings.appName.localize(),
                style = B.typography().main.splash,
            )
        }
    }
}

@Composable
@Preview
internal fun SplashScreenContent_Preview() {
    globalApplicationContext = LocalContext.current
    MainTheme {
        SplashScreenContent()
    }
}
