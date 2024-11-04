package com.chatkmm.features.authorization.di

import com.chatkmm.features.authorization.data.AuthorizationRepositoryImpl
import com.chatkmm.features.authorization.domain.AuthorizationRepository
import com.chatkmm.features.authorization.presentation.AuthorizationViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual val authorizationModule: Module = module {
    factory <AuthorizationRepository> { AuthorizationRepositoryImpl(
        authorizationApi = get(),
        keyValueStorage = get()
    ) }
    factory { AuthorizationViewModel(authorizationRepository = get()) }
}