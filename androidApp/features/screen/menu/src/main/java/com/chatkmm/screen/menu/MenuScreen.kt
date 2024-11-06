package com.chatkmm.screen.menu

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.chatkmm.base.features.enum.Screen
import com.chatkmm.data.utils.localize
import com.chatkmm.features.menu.presentation.MenuViewModel
import com.chatkmm.resources.MultiplatformResource
import com.chatkmm.root.presentation.RootViewModel
import com.chatkmm.ui.MainDialog
import com.chatkmm.ui.MainTheme
import org.koin.java.KoinJavaComponent.getKoin

@Composable
fun MenuScreen() {
    val context = LocalContext.current
    val activity = context as? ComponentActivity ?: throw IllegalStateException("Context is not an instance of ComponentActivity")

    val viewModel: MenuViewModel = remember { getKoin().get() }
    val rootViewModel: RootViewModel = remember { getKoin().get() }

    val errorText by viewModel.errorText.state.collectAsState()
    val screen by viewModel.newScreen.state.collectAsState()

    LaunchedEffect(screen) {
        if (screen != null) {
            rootViewModel.updateScreen(
                screen = screen!!,
                argumentsJson = emptyList(),
                isClear = viewModel.isClearScreen
            )
        }
    }
    MainTheme {
        MenuScreenContent(
            profileUrl = viewModel.profileUrl.state.collectAsState().value ?: "",
            connectionStatus = viewModel.connectionStatus.state.collectAsState().value,
            chats = viewModel.chats.state.collectAsState().value,
            stateScreen = viewModel.stateScreen.state.collectAsState().value,
            onSetScreen = { newScreen ->
                viewModel.setScreen(screen = newScreen, isClear = false)
            },
            onOpenChat = { chat ->
                viewModel.saveChatLocal(chat = chat)
                viewModel.setScreen(screen = Screen.CHAT, isClear = false)
            },
            onUpdate = {
                viewModel.update()
            }
        )

        if (!errorText.isNullOrEmpty()) {
            MainDialog(
                title = MultiplatformResource.strings.errorTitle.localize(),
                description = errorText
                    ?: MultiplatformResource.strings.errorDescription.localize(),
                cancelText = MultiplatformResource.strings.close.localize()
            ) {
                viewModel.update()
            }
        }
    }
}