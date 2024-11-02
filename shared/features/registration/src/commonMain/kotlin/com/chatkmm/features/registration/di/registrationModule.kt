package com.chatkmm.features.registration.di

import com.chatkmm.features.registration.presentation.RegistrationViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val registrationModule: Module = module {
    factory {
        RegistrationViewModel()
    }
}