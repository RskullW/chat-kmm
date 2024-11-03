package com.chatkmm.features.chat.presentation

import com.chatkmm.base.features.StateFlow
import com.chatkmm.base.features.ViewModel

class ChatViewModel(): ViewModel() {
    val errorText: StateFlow<String?> = StateFlow(null)

    init {

    }
}