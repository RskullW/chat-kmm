package com.chatkmm.features.chat.di

import com.chatkmm.features.chat.data.ChatRepositoryImpl
import com.chatkmm.features.chat.domain.ChatRepository
import com.chatkmm.features.chat.presentation.ChatViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val chatModule: Module = module {
    factory <ChatRepository> { ChatRepositoryImpl() }

    factory { ChatViewModel(get()) }
}