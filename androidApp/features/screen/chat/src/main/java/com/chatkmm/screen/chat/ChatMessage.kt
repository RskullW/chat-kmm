package com.chatkmm.screen.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chatkmm.base.features.enum.StatusMessage
import com.chatkmm.resources.MultiplatformResource
import com.chatkmm.ui.B
import com.chatkmm.ui.MainTheme

@Composable
fun ChatMessage(
    modifier: Modifier,
    message: String,
    timeFormatted: String,
    statusMessage: StatusMessage?,
    isMeMessage: Boolean
) {
    val maxWidth = LocalConfiguration.current.screenWidthDp.dp / 2

    val iconStatusId = when (statusMessage) {
        StatusMessage.SENDING -> {
            MultiplatformResource.images.ic_sending.drawableResId
        }
        StatusMessage.NOT_SENT -> {
            MultiplatformResource.images.ic_notsent.drawableResId
        }
        StatusMessage.READ -> {
            MultiplatformResource.images.ic_read.drawableResId
        }
        StatusMessage.SENT -> {
            MultiplatformResource.images.ic_sent.drawableResId
        }
        else -> {
            null
        }
    }

    val iconStatusColor = if (statusMessage == StatusMessage.NOT_SENT) {
        B.colors().error
    } else {
        B.colors().secondary.copy(alpha = 0.6f)
    }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = if (isMeMessage) Arrangement.End else Arrangement.Start
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .clip(RoundedCornerShape(
                    topStart = if (isMeMessage) 20.dp else 0.dp,
                    bottomStart = if (isMeMessage) 20.dp else 0.dp,
                    topEnd = if (!isMeMessage) 20.dp else 0.dp,
                    bottomEnd = if (!isMeMessage) 20.dp else 0.dp,
                ))
                .background(B.colors().white)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = maxWidth),
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(end = 12.dp),
                    text = message,
                    style = B.typography().main.main,
                    color = B.colors().secondary,
                    textAlign = TextAlign.Start,
                )

                Row(
                    modifier = Modifier
                        .offset(y = (-5).dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = timeFormatted,
                        textAlign = TextAlign.End,
                        style = B.typography().main.main,
                    )

                    if (iconStatusId != null) {
                        Icon(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .size(20.dp),
                            painter = painterResource(id = iconStatusId),
                            contentDescription = null,
                            tint = iconStatusColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
internal fun ChatMessage_Preview() {
    MainTheme {
        Column(
            modifier = Modifier
                .background(B.colors().primary)
                .fillMaxSize(),
        ) {
            ChatMessage(
                modifier = Modifier,
                message = "Привет! Привет! Привет! Привет!",
                isMeMessage = false,
                statusMessage = null,
                timeFormatted = "20:02",
            )

            ChatMessage(
                modifier = Modifier,
                message = "Привет",
                isMeMessage = true,
                statusMessage = StatusMessage.SENDING,
                timeFormatted = "20:02",
            )
        }
    }
}