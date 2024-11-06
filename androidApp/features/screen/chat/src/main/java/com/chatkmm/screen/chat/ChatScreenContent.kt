package com.chatkmm.screen.chat

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.chatkmm.base.features.enum.Screen
import com.chatkmm.base.features.enum.StatusMessage
import com.chatkmm.base.features.model.Message
import com.chatkmm.data.utils.globalApplicationContext
import com.chatkmm.data.utils.localize
import com.chatkmm.resources.MultiplatformResource
import com.chatkmm.ui.B
import com.chatkmm.ui.InputTextField
import com.chatkmm.ui.MainTheme
import com.chatkmm.ui.button.ImageButton
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ChatScreenContent(
    inputText: String,
    chatName: String,
    profileImageUrl: String,
    messages: List<Message>,
    statusMessage: StatusMessage,
    onUpdateText: (String) -> Unit,
    onSetScreen: (Screen?) -> Unit,
) {
    var bitmapItem by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(profileImageUrl) {
        if (profileImageUrl.isNotEmpty()) {
            val bitmap = withContext(Dispatchers.IO) {
                Picasso.get().load(profileImageUrl)
                    .placeholder(MultiplatformResource.images.ic_avatar.drawableResId)
                    .get()
            }

            bitmapItem = bitmap
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(B.colors().primary)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        // App Bar
        Row(
            modifier = Modifier
                .background(B.colors().white)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ImageButton(
                modifierIcon = Modifier,
                iconColor = B.colors().black,
                iconId = MultiplatformResource.images.ic_back.drawableResId
            ) {
                onSetScreen(null)
            }


            Text(
                modifier = Modifier
                    .offset(x = (-10).dp),
                text = chatName,
                color = B.colors().secondary,
                style = B.typography().main.title
            )

            if (bitmapItem != null) {
                Image(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    bitmap = bitmapItem!!.asImageBitmap(),
                    contentDescription = "profile",
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = MultiplatformResource.images.ic_profile.drawableResId),
                    contentDescription = "profile",
                    contentScale = ContentScale.Crop
                )
            }

        }

        HorizontalDivider(
            modifier = Modifier,
            color = B.colors().secondary
        )

        // Messages

        if (messages.isEmpty()) {
            Column(
                modifier = Modifier
                    .weight(1.0f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(180.dp),
                    painter = painterResource(id = MultiplatformResource.images.ic_people.drawableResId),
                    contentDescription = "empty")

                Text(
                    modifier = Modifier
                        .padding(horizontal = 24.dp),
                    text = MultiplatformResource.strings.emptyDialog.localize(),
                    textAlign = TextAlign.Center,
                    color = B.colors().secondary,
                    style = B.typography().main.main
                    )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1.0f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Bottom
            ) {
                items(messages.size) { index ->
                    val message = messages[index]

                    ChatMessage(
                        modifier = Modifier
                            .padding(bottom = 8.dp),
                        message = message.message,
                        timeFormatted = message.dateFormatted,
                        statusMessage = if (index == messages.lastIndex && message.isMeMessage) statusMessage else null,
                        isMeMessage = message.isMeMessage
                    )
                }
            }
        }

        // Bottom bar
        HorizontalDivider(
            modifier = Modifier,
            color = B.colors().secondary
        )

        Row(
            modifier = Modifier
                .background(B.colors().white)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier
                .padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
                ) {
                ImageButton(
                    modifier = Modifier
                        .padding(end = 8.dp),
                    modifierIcon = Modifier,
                    size = 24,
                    iconColor = B.colors().black,
                    iconId = MultiplatformResource.images.ic_paperclip.drawableResId
                ) {}
                ImageButton(
                    modifierIcon = Modifier,
                    size = 24,
                    iconColor = B.colors().black,
                    iconId = MultiplatformResource.images.ic_sticker.drawableResId
                ) {}
            }

            InputTextField(
                modifier = Modifier.weight(1f),
                hintText = MultiplatformResource.strings.message.localize(),
                text = inputText,
                onTextChange = onUpdateText
            )

            ImageButton(
                modifier = Modifier
                    .padding(start = 16.dp),
                modifierIcon = Modifier
                    .rotate(if (inputText.isEmpty() || inputText.isBlank()) 0f else 180f),
                size = 32,
                iconColor = B.colors().black,
                iconId = if (inputText.isEmpty() || inputText.isBlank()) MultiplatformResource.images.ic_record.drawableResId else MultiplatformResource.images.ic_back.drawableResId
            ) {}
        }


    }
}

@Composable
@Preview
internal fun ChatScreenContent_Preview() {
    globalApplicationContext = LocalContext.current

    MainTheme {
        ChatScreenContent(
            chatName = "Гро\$ный",
            profileImageUrl = "",
            inputText = "123",
            messages = listOf(
                Message(
                    message = "Привет, как дела?",
                    isMeMessage = false,
                    dateFormatted = "18:05"
                ),
                Message(
                    message = "Привет, как дела?",
                    isMeMessage = false,
                    dateFormatted = "18:05"
                ),
                Message(
                    message = "Привет, как дела?",
                    isMeMessage = false,
                    dateFormatted = "18:05"
                ),
                Message(
                    message = "Привет, как дела?",
                    isMeMessage = true,
                    dateFormatted = "18:05"
                ),
            ),
            statusMessage = StatusMessage.SENDING,
            onUpdateText = {

            },
            onSetScreen = {

            }
        )
    }
}