package com.chatkmm.root.di

import com.chatkmm.di.networkModule
import com.chatkmm.features.splash.di.splashModule
import org.koin.dsl.KoinAppDeclaration

public fun startKoin(koinAppDeclaration: KoinAppDeclaration) {
    org.koin.core.context.startKoin {
        koinAppDeclaration()
        modules(rootModule, networkModule, splashModule)
    }
}