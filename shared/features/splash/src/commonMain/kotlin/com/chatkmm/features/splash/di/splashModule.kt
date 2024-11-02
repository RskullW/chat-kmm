package com.chatkmm.features.splash.di

import com.chatkmm.features.splash.presentation.SplashViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val splashModule: Module = module {
    factory {
        SplashViewModel(
            configParams = get(),
        )
    }
}