package com.chatkmm.screen.authorization

import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.chatkmm.base.features.enum.Screen
import com.chatkmm.base.features.enum.StateScreen
import com.chatkmm.data.utils.localize
import com.chatkmm.domain.constants.Constants
import com.chatkmm.features.authorization.presentation.AuthorizationViewModel
import com.chatkmm.resources.MultiplatformResource
import com.chatkmm.root.presentation.RootViewModel
import com.chatkmm.screen.authorization.dialog.CountryDialog
import com.chatkmm.ui.B
import com.chatkmm.ui.LoadingContent
import com.chatkmm.ui.MainDialog
import com.chatkmm.ui.MainTheme
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.getKoin

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AuthorizationScreen() {
    val context = LocalContext.current
    val activity = context as? ComponentActivity ?: throw IllegalStateException("Context is not an instance of ComponentActivity")

    val viewModel: AuthorizationViewModel = remember { getKoin().get() }
    val rootViewModel: RootViewModel = remember { getKoin().get() }

    var isDialogSelectorCountry by remember { mutableStateOf(false) }
    var phoneNumber by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }

    val pagerState = rememberPagerState(
        initialPage = viewModel.currentPage.state.collectAsState().value,
        pageCount = {
            viewModel.numberOfPages
        }
    )

    val stateScreen by viewModel.stateScreen.state.collectAsState()
    val errorText by viewModel.errorText.state.collectAsState()
    val newScreen by viewModel.newScreen.state.collectAsState()

    LaunchedEffect(newScreen) {
        if (newScreen != null) {
            rootViewModel.updateScreen(
                screen = newScreen!!,
                argumentsJson = emptyList(),
                isClear = true
            )
        }
    }
    MainTheme {
        AuthorizationScreenContent(
            pagerState = pagerState,
            currentPage = viewModel.currentPage.state.collectAsState().value,
            numberOfPages = viewModel.numberOfPages,
            currentPhoneNumberCountry = viewModel.currentRegion.state.collectAsState().value,
            phoneNumber = phoneNumber,
            code = code,
            onUpdatePage = { newValue ->
                viewModel.updatePage(newValue)
            },
            onClickSelector = {
                isDialogSelectorCountry = true
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
            },
            onSetPhoneNumber = { newValue ->
                val newResult = viewModel.afterTextChanged(newValue)
                phoneNumber = newResult
            },
            onSetCode = { newValue ->
                code = viewModel.afterTextCode(newValue)
            }
        )

        if (!errorText.isNullOrEmpty()) {
            MainDialog(
                title = MultiplatformResource.strings.errorTitle.localize(),
                description = errorText ?: MultiplatformResource.strings.errorDescription.localize(),
                cancelText = MultiplatformResource.strings.close.localize()) {
                viewModel.updateErrorText()
            }
        }
        
        if (isDialogSelectorCountry) {
            CountryDialog(
                countries = viewModel.countries.state.collectAsState().value,
                onSelectItem = { newCountry ->
                    viewModel.updateCountry(newCountry = newCountry)
                    phoneNumber = viewModel.currentRegion.getValue().regionCode
                }) {
                isDialogSelectorCountry = false
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