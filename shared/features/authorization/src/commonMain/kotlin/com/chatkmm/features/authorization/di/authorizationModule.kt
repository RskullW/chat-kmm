package com.chatkmm.features.authorization.di

import com.chatkmm.features.authorization.presentation.AuthorizationViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val authorizationModule: Module = module {
    factory {
        AuthorizationViewModel()
    }
}