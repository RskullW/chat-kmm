package com.chatkmm.screen.menu

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chatkmm.base.features.enum.Screen
import com.chatkmm.base.features.enum.StateScreen
import com.chatkmm.base.features.model.Chat
import com.chatkmm.data.utils.globalApplicationContext
import com.chatkmm.data.utils.localize
import com.chatkmm.resources.MultiplatformResource
import com.chatkmm.screen.menu.chat.ChatItem
import com.chatkmm.screen.menu.chat.ChatItemShimmer
import com.chatkmm.ui.B
import com.chatkmm.ui.MainTheme
import com.chatkmm.ui.button.ImageButton
import com.squareup.picasso.Picasso
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreenContent(
    profileUrl: String,
    connectionStatus: String,
    chats: List<Chat>,
    stateScreen: StateScreen,
    onSetScreen: (Screen) -> Unit,
    onOpenChat: (Chat) -> Unit,
    onUpdate: () -> Unit,
) {
    var isLoading by remember { mutableStateOf(false) }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = {
            onUpdate()
        }
    )

    var bitmapItem by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(profileUrl) {
        if (!profileUrl.isNullOrEmpty()) {
            val bitmap = withContext(Dispatchers.IO) {
                Picasso.get().load(profileUrl)
                    .placeholder(MultiplatformResource.images.ic_avatar.drawableResId)
                    .get()
            }

            bitmapItem = bitmap
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(B.colors().primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        // AppBar
        Row(
            modifier = Modifier
                .background(B.colors().blue.copy(alpha = 0.1f))
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            if (bitmapItem == null) {
                Image(
                    modifier = Modifier
                        .background(B.colors().secondary.copy(0.3f), CircleShape)
                        .clickable {
                            onSetScreen(Screen.PROFILE)
                        }
                        .padding(4.dp)
                        .size(48.dp),
                    painter = painterResource(id = MultiplatformResource.images.ic_profile.drawableResId),
                    contentDescription = "profile"
                )
            } else {
                Image(
                    modifier = Modifier
                        .background(B.colors().secondary.copy(0.3f), CircleShape)
                        .clickable {
                            onSetScreen(Screen.PROFILE)
                        }
                        .padding(4.dp)
                        .size(48.dp),
                    bitmap = bitmapItem!!.asImageBitmap(),
                    contentDescription = "profile"
                )
            }

            Text(
                modifier = Modifier
                    .padding(start = 16.dp),
                text = connectionStatus,
                color = B.colors().secondary,
                style = B.typography().main.title
            )

            Spacer(modifier = Modifier.weight(1.0f))

            ImageButton(
                size = 32,
                modifierIcon = Modifier,
                iconId = MultiplatformResource.images.ic_search.drawableResId
            ) {
                // TODO: IB_001 to 'Search'
            }
        }

        HorizontalDivider(
            thickness = 2.dp,
            color = B.colors().secondary
        )

        Box(modifier = Modifier,
            contentAlignment = Alignment.TopStart
        ) {
            if (stateScreen != StateScreen.LOADING) {
                LazyColumn(
                    modifier = Modifier
                        .pullRefresh(pullRefreshState),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    items(chats.size) { index ->
                        ChatItem(
                            imageUrl = chats[index].imageUrl,
                            name = chats[index].name,
                            lastMessage = chats[index].messages.lastOrNull()?.message ?: MultiplatformResource.strings.startDialog.localize(), // TODO: 0_0
                            dateFormatted = chats[index].messages.lastOrNull()?.dateFormatted ?: "",
                            newMessage = if (index % 4 == 0 && chats[index].messages.isNotEmpty()) 1 else null,
                            onClick = {
                                onOpenChat(chats[index])
                            },
                            status = chats[index].status
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .pullRefresh(pullRefreshState),
                ) {
                    items(20) { index ->
                        ChatItemShimmer(
                            isLastDialog = index == 20
                        )
                    }
                }
            }

            PullRefreshIndicator(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .background(B.colors().transparent),
                refreshing = isLoading,
                state = pullRefreshState
            )
        }

    }
}

@Composable
@Preview
internal fun MenuScreenContent_Preview() {
    globalApplicationContext = LocalContext.current

    MainTheme {
        MenuScreenContent(
            stateScreen = StateScreen.LOADING,
            connectionStatus = "Соединение..",
            chats = listOf(),
            profileUrl = "",
            onSetScreen = {

            },
            onOpenChat = {

            },
            onUpdate = {
                
            }
        )
    }
}