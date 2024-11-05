package com.chatkmm.features.menu.domain.database

import com.chatkmm.features.menu.domain.dto.ChatDto
import com.chatkmm.features.menu.domain.dto.MessageDto

actual class ChatDao {
    actual suspend fun insertChat(chat: ChatDto) {
    }

    actual suspend fun insertMessages(messages: List<MessageDto>) {
    }

    actual suspend fun getChats(): List<ChatDto> {
        TODO("Not yet implemented")
    }

    actual suspend fun getMessagesByChatId(chatId: Long): List<MessageDto> {
        TODO("Not yet implemented")
    }

    actual suspend fun deleteChat(chatId: Long) {
    }
}