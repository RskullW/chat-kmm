package com.chatkmm.data.utils

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc

actual fun StringResource.localize(): String {
    return StringDesc.Resource(this).localized()
}

actual fun StringResource.format(value: String): String {
    return StringDesc.ResourceFormatted(this, value).localized()
}