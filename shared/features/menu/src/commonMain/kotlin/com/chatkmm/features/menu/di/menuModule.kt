package com.chatkmm.features.menu.di

import com.chatkmm.features.menu.presentation.MenuViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val menuModule: Module = module {
    factory {
        MenuViewModel()
    }
}