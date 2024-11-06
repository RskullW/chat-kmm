package com.chatkmm.screen.chat

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.chatkmm.base.features.enum.Screen
import com.chatkmm.base.features.enum.StateScreen
import com.chatkmm.base.features.enum.StatusMessage
import com.chatkmm.data.utils.localize
import com.chatkmm.features.chat.presentation.ChatViewModel
import com.chatkmm.resources.MultiplatformResource
import com.chatkmm.root.presentation.RootViewModel
import com.chatkmm.ui.B
import com.chatkmm.ui.LoadingContent
import com.chatkmm.ui.MainDialog
import com.chatkmm.ui.MainTheme
import org.koin.java.KoinJavaComponent.getKoin

@Composable
fun ChatScreen() {
    val context = LocalContext.current

    val viewModel: ChatViewModel = remember { getKoin().get() }
    val rootViewModel: RootViewModel = remember { getKoin().get() }
    var inputText by remember { mutableStateOf("") }

    val errorText by viewModel.errorText.state.collectAsState()
    val stateScreen by viewModel.stateScreen.state.collectAsState()
    val isDeadToken by viewModel.isDeadToken.state.collectAsState()

    LaunchedEffect(isDeadToken) {
        if (isDeadToken == true) {
            rootViewModel.updateScreen(
                screen = Screen.AUTHORIZATION,
                argumentsJson = emptyList(),
                isClear = true
            )
        }
    }

    MainTheme {
        ChatScreenContent(
            inputText = inputText,
            chatName = viewModel.chatName.state.collectAsState().value,
            profileImageUrl = viewModel.profileImageUrl.state.collectAsState().value,
            messages = viewModel.messages.state.collectAsState().value,
            statusMessage = StatusMessage.SENDING,
            onUpdateText = { newValue ->
                inputText = newValue
            },
            onSetScreen = { newScreen ->
                if (newScreen == null) {
                    rootViewModel.finishScreen()
                } else {
                    rootViewModel.updateScreen(
                        screen = newScreen,
                        argumentsJson = emptyList(),
                        isClear = true
                    )
                }
            }
        )

        if (!errorText.isNullOrEmpty()) {
            MainDialog(
                title = MultiplatformResource.strings.errorTitle.localize(),
                description = errorText ?: MultiplatformResource.strings.errorDescription.localize(),
                cancelText = MultiplatformResource.strings.close.localize()) {
                viewModel.update()
            }
        }

        if (stateScreen == StateScreen.LOADING) {
            Box(modifier = Modifier
                .background(B.colors().black.copy(alpha = 0.8f))
                .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingContent()
            }
        }
    }
}