package com.chatkmm.screen.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chatkmm.ui.B
import com.chatkmm.ui.MainTheme
import kotlinx.coroutines.delay

@Composable
fun ScalingRotatingLoader() {
  var rotateOuter by remember { mutableStateOf(false) }
  val angle by animateFloatAsState(
    targetValue = if (rotateOuter) 360 * 3f else 0f,
    animationSpec = spring(
      visibilityThreshold = 0.3f,
      dampingRatio = 0.1f,
      stiffness = 0.87f
    )
  )
  val scaleBox = remember { androidx.compose.animation.core.Animatable(0.3f) }

  LaunchedEffect(Unit) {
    scaleBox.animateTo(
      targetValue = 10f,
      animationSpec = tween(durationMillis = 2000)
    )
  }

  val centerOffset = with(LocalDensity.current) {
    Offset(
      LocalConfiguration.current.screenWidthDp.div(2).dp.toPx(),
      LocalConfiguration.current.screenHeightDp.div(2).dp.toPx()
    )
  }

  LaunchedEffect(key1 = true, block = {
    rotateOuter = !rotateOuter
    while (true){
      delay(2000)
      rotateOuter = !rotateOuter
    }
  })


  Box {
      Box(
        Modifier
          .scale(scaleBox.value)
          .align(Alignment.Center)
      ) {
        Box(
          Modifier
            .align(Alignment.Center)
            .size(50.dp)
            .background(Color.White, shape = CircleShape)
        )
        Box(Modifier.rotate(angle)) {
          Canvas(modifier = Modifier
            .align(Alignment.Center)
            .size(100.dp), onDraw = {
            drawArc(
              color =
              Color.White,
              style = Stroke(
                width = 3f,
                cap = StrokeCap.Round,
                join =
                StrokeJoin.Round,
              ),
              startAngle = 180f,
              sweepAngle = 288f,
              useCenter = false
            )

          })
        }

        Box(Modifier.rotate(angle)) {
          Canvas(modifier = Modifier
            .rotate(180f)
            .align(Alignment.Center)
            .size(100.dp), onDraw = {
            drawArc(
              color =
              Color.Blue,
              style = Stroke(
                width = 3f,
                cap = StrokeCap.Round,
                join =
                StrokeJoin.Round,
              ),
              startAngle = 180f,
              sweepAngle = 288f,
              useCenter = false
            )
          }
          )
        }

      }


  }
}

@Composable
@Preview
internal fun ScalingRotatingLoader_Preview() {
  MainTheme {
    Box(
      modifier = Modifier
        .fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      ScalingRotatingLoader()
    }
  }
}