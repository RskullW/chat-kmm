package com.chatkmm.features.menu.di

import com.chatkmm.features.menu.data.MenuRepositoryImpl
import com.chatkmm.features.menu.domain.MenuRepository
import com.chatkmm.features.menu.presentation.MenuViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val menuModule: Module = module {
    factory <MenuRepository> { MenuRepositoryImpl(get()) }
    factory { MenuViewModel(get()) }
}