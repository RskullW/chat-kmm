package com.chatkmm.ui.indicator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.chatkmm.ui.B
import com.chatkmm.ui.MainTheme

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    currentPage: Int,
    numberOfPages: Int,
    selectedColor: Color = B.colors().primary,
    defaultColor: Color = B.colors().gray,
    defaultRadius: Dp = 20.dp,
    selectedLength: Dp = 60.dp,
    space: Dp = 30.dp,
    animationDurationInMillis: Int = 300,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space),
        modifier = modifier,
    ) {
        for (i in 0 until numberOfPages) {
            val isSelected = i == (currentPage-1)
            PageIndicatorView(
                isSelected = isSelected,
                selectedColor = selectedColor,
                defaultColor = defaultColor,
                defaultRadius = defaultRadius,
                selectedLength = selectedLength,
                animationDurationInMillis = animationDurationInMillis,
            )
        }
    }
}

@Composable
@Preview
internal fun PageIndicator_Preview() {
    MainTheme {
        Box(modifier = Modifier) {
            PageIndicator(currentPage = 4, numberOfPages = 4)
        }
    }
}