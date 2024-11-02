package com.chatkmm.features.splash.presentation

import com.chatkmm.base.features.StateFlow
import com.chatkmm.base.features.ViewModel
import com.chatkmm.data.model.ConfigParams


class SplashViewModel(val configParams: ConfigParams): ViewModel() {
    val errorText: StateFlow<String?> = StateFlow(null)
}