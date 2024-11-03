package com.chatkmm.ui.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.chatkmm.resources.MultiplatformResource
import com.chatkmm.ui.B
import com.chatkmm.ui.MainTheme

@Composable
fun ImageButton(
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier,
    modifierIcon: Modifier,
    iconId: Int,
    size: Int = 48,
    backgroundColor: Color = B.colors().transparent,
    iconColor: Color = B.colors().thirdly,
    clipDp: Dp = 1000.dp,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(clipDp))
            .size(size.dp)
            .background(if (isEnabled) backgroundColor else B.colors().border)
            .clickable(enabled = isEnabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (iconId != null) {
            Icon(
                modifier = modifierIcon
                    .fillMaxSize(),
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = iconColor
            )
        }
    }
}

@Composable
fun ImageButton(
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier,
    iconId: Int,
    size: Int = 48,
    iconColor: Color = B.colors().secondary,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(size.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isEnabled) B.colors().primary else B.colors().border)
            .clickable(enabled = isEnabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (iconId != null) {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                ,
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = iconColor
            )
        }
    }
}


@Composable
@Preview
internal fun ImageButton_Preview() {
    MainTheme {
        Column {
            ImageButton(
                modifier = Modifier.padding(bottom = 16.dp),
                isEnabled = true, modifierIcon = Modifier
                    .padding(8.dp),
                iconId = MultiplatformResource.images.placeholder.drawableResId
            ) {}
        }
    }
}