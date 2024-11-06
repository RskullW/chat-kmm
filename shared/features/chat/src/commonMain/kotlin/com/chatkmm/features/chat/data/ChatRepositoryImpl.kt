package com.chatkmm.features.chat.data

import com.chatkmm.base.features.model.Chat
import com.chatkmm.data.model.transferArguments
import com.chatkmm.domain.constants.TransferArguments
import com.chatkmm.features.chat.domain.ChatRepository

class ChatRepositoryImpl: ChatRepository {
    private val chat: Chat? = transferArguments[TransferArguments.localChat] as? Chat

    override suspend fun getMessages() = chat?.messages ?: emptyList()
    override suspend fun getProfileUrl() = chat?.imageUrl ?: ""
    override suspend fun getName() = chat?.name ?: ""
}