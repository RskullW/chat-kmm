package com.chatkmm.screen.menu.chat

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chatkmm.base.features.enum.StatusMessage
import com.chatkmm.resources.MultiplatformResource
import com.chatkmm.ui.B
import com.chatkmm.ui.MainTheme
import com.chatkmm.ui.modifier.shimmer
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ChatItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    name: String,
    lastMessage: String,
    dateFormatted: String,
    status: StatusMessage?,
    newMessage: Int?,
    isLastDialog: Boolean = false,
    onClick: () -> Unit,
) {
    var bitmapItem by remember { mutableStateOf<Bitmap?>(null) }

    val iconStatusId = when (status) {
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

    val iconStatusColor = if (status == StatusMessage.NOT_SENT) {
        B.colors().error
    } else {
        B.colors().blue.copy(alpha = 0.6f)
    }

    LaunchedEffect(imageUrl) {
        if (!imageUrl.isNullOrEmpty()) {
            val bitmap = withContext(Dispatchers.IO) {
                Picasso.get().load(imageUrl)
                    .placeholder(MultiplatformResource.images.ic_avatar.drawableResId)
                    .get()
            }

            bitmapItem = bitmap
        }
    }

    Column(modifier = modifier
        .clickable {
            onClick()
        }
        .background(B.colors().blue.copy(alpha = 0.1f))
        .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            if (bitmapItem == null) {
                Image(
                    modifier = Modifier
                        .background(B.colors().secondary.copy(0.3f), CircleShape)
                        .padding(4.dp)
                        .size(32.dp),
                    painter = painterResource(id = MultiplatformResource.images.ic_avatar.drawableResId),
                    contentDescription = "avatar_$name"
                )
            }
            else {
                Image(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    bitmap = bitmapItem!!.asImageBitmap(),
                    contentDescription = "avatar_$name",
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .weight(1.0f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Start
                ) {

                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, bottom = 4.dp),
                        text = name,
                        style = B.typography().chat.name,
                        color = B.colors().secondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (iconStatusId != null) {
                        Icon(
                            modifier = Modifier
                                .size(18.dp),
                            painter = painterResource(id = iconStatusId),
                            contentDescription = "status",
                            tint = iconStatusColor,
                        )
                    }
                }

                Text(
                    modifier = Modifier
                        .padding(start = 8.dp),
                    text = lastMessage,
                    style = B.typography().chat.message,
                    color = B.colors().secondary.copy(alpha = 0.6f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }


            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End,
            ) {

                Text(
                    modifier = Modifier
                        .padding(start = 8.dp, bottom = 4.dp),
                    text = dateFormatted,
                    style = B.typography().chat.date,
                    color = B.colors().secondary.copy(alpha = 0.6f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (newMessage != null) {
                    Text(
                        modifier = Modifier
                            .background(B.colors().secondary.copy(alpha = 0.6f), CircleShape)
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        text = newMessage.toString(),
                        style = B.typography().chat.message,
                        color = B.colors().white,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        if (!isLastDialog) {
            HorizontalDivider(
                modifier = Modifier,
                thickness = 1.dp,
                color = B.colors().black.copy(alpha = 0.3f)
            )
        }
    }
}

@Composable
fun ChatItemShimmer(
    modifier: Modifier = Modifier,
    isLastDialog: Boolean = false,
) {
    Column(modifier = modifier
        .shimmer(
            angle = 45f,
            durationMillis = 3000,
        )
        .background(B.colors().blue.copy(0.1f))
        .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .shimmer(clipRadius = 1000.dp, angle = 45f)
                    .background(B.colors().gray.copy(alpha = 0.2f))
                    .padding(4.dp)
                    .size(32.dp)
            ) {}

            Column(
                modifier = Modifier
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .shimmer()
                        .background(B.colors().gray.copy(alpha = 0.2f))
                        .size(width = 80.dp, height = 15.dp)
                    ,
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Start
                ) {}

                Box(modifier = Modifier
                    .shimmer()
                    .background(B.colors().gray.copy(alpha = 0.2f))
                    .size(width = 120.dp, height = 15.dp))
            }

            Spacer(modifier = Modifier.weight(1.0f))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End,
            ) {

                Box(modifier = Modifier
                    .size(width = 40.dp, height = 15.dp))
            }
        }

        if (!isLastDialog) {
            HorizontalDivider(
                modifier = Modifier,
                thickness = 1.dp,
                color = B.colors().black.copy(alpha = 0.3f)
            )
        }
    }
}
@Composable
@Preview
internal fun ChatItem_Preview() {
    MainTheme {
        Column(
            modifier = Modifier
                .background(B.colors().primary)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ChatItem(
                imageUrl = "",
                name = "Иванов",
                lastMessage = "А ты пользуешься Mango Chat? А ты пользуешься Mango Chat?",
                dateFormatted = "18:24",
                newMessage = 2,
                onClick = {

                },
                status = StatusMessage.SENDING
            )

            ChatItem(
                imageUrl = "",
                name = "Иванов",
                lastMessage = "А ты пользуешься Mango Chat?",
                dateFormatted = "18:24",
                newMessage = 3,
                onClick = {

                },
                status = StatusMessage.READ
            )

            ChatItem(
                imageUrl = "",
                name = "Иванов",
                lastMessage = "А ты пользуешься Mango Chat?",
                dateFormatted = "18:24",
                newMessage = null,
                isLastDialog = false,
                onClick = {

                },
                status = StatusMessage.NOT_SENT
            )

            ChatItemShimmer()
            ChatItemShimmer()
            ChatItemShimmer(isLastDialog = true)
        }
    }
}