package com.chatkmm.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp


@Composable
fun LoadingContent(
    color: Color = B.colors().primary
) {
    CircularProgressIndicator(
        modifier = Modifier.size(22.dp),
        color = color,
        trackColor = B.colors().transparent,
        strokeWidth = 2.dp,
        strokeCap = StrokeCap.Square
    )
}