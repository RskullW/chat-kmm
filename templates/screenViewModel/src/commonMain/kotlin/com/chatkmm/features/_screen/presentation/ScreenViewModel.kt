package com.chatkmm.features.screen.presentation

import com.chatkmm.base.features.StateFlow
import com.chatkmm.base.features.ViewModel

class ScreenViewModel(): ViewModel() {
    val errorText: StateFlow<String?> = StateFlow(null)

    init {

    }
}