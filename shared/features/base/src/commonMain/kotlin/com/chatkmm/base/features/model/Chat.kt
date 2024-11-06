package com.chatkmm.base.features.model

import com.chatkmm.base.features.enum.StatusMessage

data class Chat(
    val imageUrl: String,
    val name: String,
    val messages: List<Message>,
    val status: StatusMessage?,
    val newMessage: Int?,
)