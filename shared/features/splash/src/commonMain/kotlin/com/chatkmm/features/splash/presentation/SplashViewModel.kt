package com.chatkmm.features.splash.presentation

import com.chatkmm.base.features.StateFlow
import com.chatkmm.base.features.ViewModel
import com.chatkmm.base.features.enum.Screen
import com.chatkmm.data.model.ConfigParams
import com.chatkmm.data.model.CustomResponseException
import com.chatkmm.data.utils.Log
import com.chatkmm.data.utils.localize
import com.chatkmm.domain.constants.Constants
import com.chatkmm.features.splash.domain.SplashRepository
import com.chatkmm.resources.MultiplatformResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashViewModel(val splashRepository: SplashRepository): ViewModel() {
    val errorText: StateFlow<String?> = StateFlow(null)
    val newScreen: StateFlow<Screen?> = StateFlow(null)

    init {
        update()
    }

    public fun update() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val isAuthorized = splashRepository.isAuthorized()

                withContextMain {
                    delay(Constants.Numbers.splashDelay)
                    newScreen.update(if (isAuthorized) Screen.MENU else Screen.AUTHORIZATION)
                }
            } catch (e: Throwable) {
                withContextMain {
                    errorText.update(e.message)
                }
            }
        }
    }

    public fun updateErrorText(value: String? = null) {
        errorText.update(value = value)
    }
}