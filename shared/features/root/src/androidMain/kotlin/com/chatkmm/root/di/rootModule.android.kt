package com.chatkmm.root.di

import android.content.Context
import com.chatkmm.data.infrastructure.ConfigAppProvider
import com.chatkmm.data.infrastructure.KeyValueStorage
import com.chatkmm.data.model.ConfigParams
import com.chatkmm.root.presentation.RootViewModel
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val rootModule: Module = module {
    single<Settings> {
        SharedPreferencesSettings(
            androidContext().getSharedPreferences("app", Context.MODE_PRIVATE)
        )
    }

    single { KeyValueStorage(settings = get()) }
    single { ConfigAppProvider(keyValueStorage = get()) }
    single { ConfigParams(keyValueStorage = get(), configAppProvider = get()) }

    single {
        RootViewModel()
    }

}