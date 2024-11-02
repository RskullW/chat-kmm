package com.chatkmm.data.infrastructure

expect class ConfigAppProvider {
    val versionApp: String
    var isFirstLaunch: Boolean
    val keyValueStorage: KeyValueStorage
}