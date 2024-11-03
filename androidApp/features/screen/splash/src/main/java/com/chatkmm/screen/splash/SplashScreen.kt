package com.chatkmm.screen.splash

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.chatkmm.base.features.enum.Screen
import com.chatkmm.data.utils.localize
import com.chatkmm.features.splash.presentation.SplashViewModel
import com.chatkmm.resources.MultiplatformResource
import com.chatkmm.root.presentation.RootViewModel
import com.chatkmm.ui.MainDialog
import com.chatkmm.ui.MainTheme
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.getKoin

@Composable
fun SplashScreen() {
    val context = LocalContext.current
    val activity = context as? ComponentActivity ?: throw IllegalStateException("Context is not an instance of ComponentActivity")

    val viewModel: SplashViewModel = remember { getKoin().get { parametersOf(activity) } }
    val rootViewModel: RootViewModel = remember { getKoin().get() }

    val errorText by viewModel.errorText.state.collectAsState()
    val newScreen by viewModel.newScreen.state.collectAsState()

    LaunchedEffect(newScreen) {
        if (newScreen != null) {
            try {
                rootViewModel.updateScreen(
                    screen = newScreen ?: throw Throwable(message = MultiplatformResource.strings.errorDescription.localize()),
                    argumentsJson = emptyList(),
                    isClear = true
                )

            } catch (e: Throwable) {
                viewModel.updateErrorText(value = e.message)
            }
        }
    }

    MainTheme {
        SplashScreenContent()

        if (!errorText.isNullOrEmpty()) {
            MainDialog(
                title = MultiplatformResource.strings.errorTitle.localize(),
                description = errorText ?: MultiplatformResource.strings.errorDescription.localize(),
                cancelText = MultiplatformResource.strings.close.localize()) {
                viewModel.updateErrorText()
                activity.finish()
            }
        }
    }
}