package com.chatkmm.root.di

import com.chatkmm.di.networkModule
import com.chatkmm.features.authorization.di.authorizationModule
import com.chatkmm.features.chat.di.chatModule
import com.chatkmm.features.menu.di.menuModule
import com.chatkmm.features.profile.di.profileModule
import com.chatkmm.features.registration.di.registrationModule
import com.chatkmm.features.splash.di.splashModule
import com.chatkmm.features.user.di.customerModule
import org.koin.dsl.KoinAppDeclaration

public fun startKoin(koinAppDeclaration: KoinAppDeclaration) {
    org.koin.core.context.startKoin {
        koinAppDeclaration()
        modules(rootModule, networkModule, splashModule, authorizationModule, chatModule, customerModule, menuModule, profileModule, registrationModule)
    }
}