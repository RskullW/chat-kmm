package com.chatkmm.data.model

import com.chatkmm.data.infrastructure.ConfigAppProvider
import com.chatkmm.data.infrastructure.KeyValueStorage

data class ConfigParams(
    val keyValueStorage: KeyValueStorage,
    val configAppProvider: ConfigAppProvider,
)