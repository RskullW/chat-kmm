package com.chatkmm.features.profile.di

import com.chatkmm.features.profile.presentation.ProfileViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val profileModule: Module = module {
    factory {
        ProfileViewModel()
    }
}