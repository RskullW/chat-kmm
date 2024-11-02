package com.chatkmm.features.menu.presentation

import com.chatkmm.base.features.StateFlow
import com.chatkmm.base.features.ViewModel

class MenuViewModel(): ViewModel() {
    val errorText: StateFlow<String?> = StateFlow(null)

    init {

    }
}