package com.chatkmm.features.registration.di

import com.chatkmm.features.registration.data.RegistrationRepositoryImpl
import com.chatkmm.features.registration.domain.RegistrationRepository
import com.chatkmm.features.registration.presentation.RegistrationViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val registrationModule: Module = module {
    factory <RegistrationRepository> { RegistrationRepositoryImpl(
        authorizationApi = get(),
        keyValueStorage = get()
    )}
    factory {
        RegistrationViewModel(
            registrationRepository = get()
        )
    }
}