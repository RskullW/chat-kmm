package com.chatkmm.features.profile.presentation

import com.chatkmm.base.features.StateFlow
import com.chatkmm.base.features.ViewModel

class ProfileViewModel(): ViewModel() {
    val errorText: StateFlow<String?> = StateFlow(null)

    init {

    }
}