package com.chatkmm.features.registration.presentation

import com.chatkmm.base.features.StateFlow
import com.chatkmm.base.features.ViewModel

class RegistrationViewModel(): ViewModel() {
    val errorText: StateFlow<String?> = StateFlow(null)

    init {

    }
}