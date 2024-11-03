package com.chatkmm.ui.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chatkmm.resources.MultiplatformResource
import com.chatkmm.ui.B
import com.chatkmm.ui.MainTheme
import com.chatkmm.ui.invert

@Composable
fun MainButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = B.colors().white,
    textStyle: TextStyle = B.typography().main.buttonText,
    backgroundColor: Color = B.colors().primary,
    disabledColor: Color = B.colors().gray,
    isLoading: Boolean = false,
    isEnabled: Boolean = true,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .background(if (isEnabled) backgroundColor else disabledColor)
            .fillMaxWidth()
            .height(56.dp)
            .clickable {
                if (isEnabled && !isLoading) {
                    onClick()
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(22.dp),
                color = if (isEnabled) B.colors().white else B.colors().thirdly,
                trackColor = B.colors().transparent,
                strokeWidth = 2.dp,
                strokeCap = StrokeCap.Square
            )
        } else {
            Text(
                text = text,
                style = textStyle,
                color = if (isEnabled) textColor else textColor.invert(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun MainButtonIcon(
    modifier: Modifier = Modifier,
    text: String,
    icon: Painter,
    textColor: Color = B.colors().white,
    iconColor: Color = B.colors().white,
    textStyle: TextStyle = B.typography().main.buttonText,
    backgroundColor: Color = B.colors().primary,
    disabledColor: Color = B.colors().gray,
    isLoading: Boolean = false,
    isEnabled: Boolean = true,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .background(if (isEnabled) backgroundColor else disabledColor)
            .fillMaxWidth()
            .height(56.dp)
            .clickable {
                if (isEnabled && !isLoading) {
                    onClick()
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(22.dp),
                color = if (isEnabled) B.colors().white else B.colors().thirdly,
                trackColor = B.colors().transparent,
                strokeWidth = 2.dp,
                strokeCap = StrokeCap.Square
            )
        } else {
            Row(modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {

                Text(
                    text = text,
                    style = textStyle,
                    color = if (isEnabled) textColor else textColor.invert()
                )

                VerticalDivider(
                    modifier = Modifier
                        .padding(16.dp),
                    color = B.colors().white
                )

                Icon(
                    modifier = Modifier.padding(vertical = 8.dp),
                    painter = icon,
                    tint = iconColor,
                    contentDescription = null
                )



            }
        }
    }
}

@Preview
@Composable
internal fun MainButton_Preview() {
    MainTheme {
        Column(modifier = Modifier
            .background(B.colors().white)
            .padding(32.dp)
        ) {
            MainButton(
                modifier = Modifier
                    .padding(bottom = 16.dp),
                text = "First Button", onClick = {})
            MainButton(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .shadow(elevation = 5.dp),
                text = "Создать аккаунт",
                backgroundColor = B.colors().secondary,) {
            }

            MainButtonIcon(
                modifier = Modifier
                    .padding(bottom = 16.dp),
                icon = painterResource(id = MultiplatformResource.images.placeholder.drawableResId),
                iconColor = B.colors().white,
                text = "15$",
                onClick = {})
        }
    }
}