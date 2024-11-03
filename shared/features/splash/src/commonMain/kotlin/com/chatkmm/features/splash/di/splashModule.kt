package com.chatkmm.features.splash.di

import com.chatkmm.features.splash.data.SplashRepositoryImpl
import com.chatkmm.features.splash.domain.SplashRepository
import com.chatkmm.features.splash.presentation.SplashViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val splashModule: Module = module {
    factory <SplashRepository> {
        SplashRepositoryImpl(usersApi = get(), configParams = get())
    }
    factory {
        SplashViewModel(splashRepository = get())
    }
}