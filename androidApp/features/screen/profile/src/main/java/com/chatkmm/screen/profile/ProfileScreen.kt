package com.chatkmm.screen.profile

import android.widget.Toast
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
import com.chatkmm.features.profile.presentation.ProfileViewModel
import com.chatkmm.resources.MultiplatformResource
import com.chatkmm.root.presentation.RootViewModel
import com.chatkmm.ui.B
import com.chatkmm.ui.LoadingContent
import com.chatkmm.ui.MainDialog
import com.chatkmm.ui.MainTheme
import org.koin.java.KoinJavaComponent.getKoin

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val activity = context as? ComponentActivity ?: throw IllegalStateException("Context is not an instance of ComponentActivity")

    val viewModel: ProfileViewModel = remember { getKoin().get() }
    val rootViewModel: RootViewModel = remember { getKoin().get() }

    var name by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var aboutMe by remember { mutableStateOf("") }
    var isDialogExit by remember { mutableStateOf(false) }

    val oldName by viewModel.oldName.state.collectAsState()
    val oldBirthday by viewModel.oldBirthday.state.collectAsState()
    val oldCity by viewModel.oldCity.state.collectAsState()
    val oldAboutMe by viewModel.oldAboutMe.state.collectAsState()

    val errorText by viewModel.errorText.state.collectAsState()
    val stateScreen by viewModel.stateScreen.state.collectAsState()
    val isDeadToken by viewModel.isDeadToken.state.collectAsState()
    val isSaved by viewModel.isSaved.state.collectAsState()
    val newScreen by viewModel.newScreen.state.collectAsState()

    LaunchedEffect(oldName) {
        name = oldName ?: ""
    }
    LaunchedEffect(oldBirthday) {
        birthday = oldBirthday ?: ""
    }
    LaunchedEffect(oldCity) {
        city = oldCity ?: ""
    }
    LaunchedEffect(oldAboutMe) {
        aboutMe = oldAboutMe ?: ""
    }
    LaunchedEffect(isDeadToken, isSaved) {
        if (isDeadToken == true) {
            viewModel.updateScreen(screen = Screen.AUTHORIZATION, isClear = true)
        }

        if (isSaved) {
            Toast.makeText(context, MultiplatformResource.strings.saved.localize(), Toast.LENGTH_SHORT).show()
            viewModel.update(isLoading = false)
        }
    }
    LaunchedEffect(newScreen) {
        if (newScreen != null) {
            rootViewModel.updateScreen(
                screen = newScreen!!,
                argumentsJson = emptyList(),
                isClear = viewModel.isClearScreen
            )
        }
    }

    MainTheme {
        ProfileScreenContent(
            name = name,
            city = city,
            birthday = birthday,
            aboutMe = aboutMe,
            userName = viewModel.userName.state.collectAsState().value,
            phoneNumber = viewModel.phoneNumber.state.collectAsState().value,
            zodiac = viewModel.zodiac.state.collectAsState().value,
            isEnabledButton = viewModel.isEnabledButton.state.collectAsState().value,
            profileImageUrl = "",
            onNameChange = { newValue ->
                name = viewModel.afterTextChangedName(newValue = newValue)
            },
            onBirthdayChange = { newValue ->
                birthday = viewModel.afterTextChangedBirthday(newValue = newValue)
            },
            onCityChange = { newValue ->
                city = viewModel.afterTextChangedCity(newValue = newValue)
            },
            onAboutMeChange = { newValue ->
                aboutMe = viewModel.afterTextChangedAboutMe(newValue = newValue)
            },
            onSetImage = {

            },
            onSave = {
                viewModel.save(
                    name = name,
                    birthday = birthday,
                    city = city,
                    aboutMe = aboutMe
                )
            },
            onExit = {
                isDialogExit = true
            },
            onSetScreen = { newScreen ->
                if (newScreen == null) {
                    rootViewModel.finishScreen()
                } else {
                    viewModel.updateScreen(
                        screen = newScreen,
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

        if (isDialogExit) {
            MainDialog(
                title = MultiplatformResource.strings.profileExit_title.localize(),
                description = errorText ?: MultiplatformResource.strings.profileExit_description.localize(),
                confirmText = MultiplatformResource.strings.profileExit_confirm.localize(),
                confirmAction = {
                    viewModel.exitProfile()
                },
                cancelText = MultiplatformResource.strings.profileExit_cancel.localize(),
                cancelAction = {
                    isDialogExit = false
                },
                )
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