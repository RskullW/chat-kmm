package com.chatkmm.features.menu.domain.dto

import com.chatkmm.base.features.enum.StatusMessage
import com.chatkmm.base.features.model.Chat

data class ChatDto(
    val id: Long,
    val imageUrl: String,
    val name: String,
    val status: StatusMessage,
    val newMessage: Int?,
    val messages: List<MessageDto>,
)

fun ChatDto.toChat(): Chat {
    return Chat(
        imageUrl = imageUrl,
        name = name,
        status = StatusMessage.READ,
        newMessage = this.newMessage,
        messages = this.messages.map { it.toMessage() },
    )
}