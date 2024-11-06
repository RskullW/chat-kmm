package com.chatkmm.features.chat.domain

import com.chatkmm.base.features.model.Message

interface ChatRepository {
    public suspend fun getMessages(): List<Message>
    public suspend fun getProfileUrl(): String
    public suspend fun getName(): String
}