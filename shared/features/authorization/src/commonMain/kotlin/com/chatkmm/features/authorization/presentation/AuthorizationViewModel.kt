package com.chatkmm.features.authorization.presentation

import com.chatkmm.base.features.StateFlow
import com.chatkmm.base.features.ViewModel

class AuthorizationViewModel(): ViewModel() {
    val errorText: StateFlow<String?> = StateFlow(null)

    init {

    }
}