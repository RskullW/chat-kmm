package com.chatkmm.screen.registration

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
import com.chatkmm.data.utils.localize
import com.chatkmm.features.registration.presentation.RegistrationViewModel
import com.chatkmm.resources.MultiplatformResource
import com.chatkmm.root.presentation.RootViewModel
import com.chatkmm.ui.B
import com.chatkmm.ui.LoadingContent
import com.chatkmm.ui.MainDialog
import com.chatkmm.ui.MainTheme
import org.koin.java.KoinJavaComponent.getKoin

@Composable
fun RegistrationScreen() {
    val context = LocalContext.current
    val activity = context as? ComponentActivity ?: throw IllegalStateException("Context is not an instance of ComponentActivity")

    val viewModel: RegistrationViewModel = remember { getKoin().get() }
    val rootViewModel: RootViewModel = remember { getKoin().get() }

    var name: String by remember { mutableStateOf("") }
    var username: String by remember { mutableStateOf("") }

    val stateScreen by viewModel.stateScreen.state.collectAsState()
    val isSuccess by viewModel.isSuccess.state.collectAsState()

    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            rootViewModel.updateScreen(
                screen = Screen.MENU,
                argumentsJson = emptyList(),
                isClear = true
            )
        }
    }

    MainTheme {
        RegistrationScreenContent(
            phoneNumber = viewModel.phoneNumber,
            name = name,
            username = username,
            errorText = viewModel.errorText.state.collectAsState().value,
            onSetName = { newValue ->
                name = newValue

                viewModel.updateErrorText()
            },
            onSetUsername = { newValue ->
                username = newValue
                viewModel.updateErrorText()
            },
            onRegister = {
                viewModel.register(
                    name = name,
                    username = username
                )
            }
        )

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