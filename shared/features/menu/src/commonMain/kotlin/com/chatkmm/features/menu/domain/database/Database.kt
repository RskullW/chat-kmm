package com.chatkmm.features.menu.domain.database

import com.chatkmm.features.menu.domain.dto.ChatDto
import com.chatkmm.features.menu.domain.dto.MessageDto

expect class ChatDao {
    suspend fun insertChat(chat: ChatDto)
    suspend fun insertMessages(messages: List<MessageDto>)
    suspend fun getChats(): List<ChatDto>
    suspend fun getMessagesByChatId(chatId: Long): List<MessageDto>
    suspend fun deleteChat(chatId: Long)
}