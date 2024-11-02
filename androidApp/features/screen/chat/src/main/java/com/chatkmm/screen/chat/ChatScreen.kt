package com.chatkmm.screen.chat

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.chatkmm.features.chat.presentation.ChatViewModel
import com.chatkmm.root.presentation.RootViewModel
import com.chatkmm.ui.MainTheme
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.getKoin

@Composable
fun ChatScreen() {
    val context = LocalContext.current
    val activity = context as? ComponentActivity ?: throw IllegalStateException("Context is not an instance of ComponentActivity")

    val viewModel: ChatViewModel = remember { getKoin().get() }
    val rootViewModel: RootViewModel = remember { getKoin().get() }

    val errorText by viewModel.errorText.state.collectAsState()

    LaunchedEffect(errorText) {
        when {
            errorText != null -> {

            }
        }
    }

    MainTheme {
        ChatScreenContent()
    }
}