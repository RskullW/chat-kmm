package com.chatkmm.data.infrastructure

actual class ConfigAppProvider(actual val keyValueStorage: KeyValueStorage) {
    actual val versionApp: String
        get() = TODO("Not yet implemented")
    actual var isFirstLaunch: Boolean = keyValueStorage.firstLaunch.let { true }
}