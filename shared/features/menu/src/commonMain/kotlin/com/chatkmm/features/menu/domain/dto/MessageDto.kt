package com.chatkmm.features.menu.domain.dto

import com.chatkmm.base.features.model.Message

data class MessageDto(
    val chatId: Long,
    val sender: String,
    val content: String,
    val timestamp: Long
)

fun MessageDto.toMessage(): Message {
    return Message(
        message = this.content,
        isMeMessage = false,
        dateFormatted = "18:00"
    )
}

