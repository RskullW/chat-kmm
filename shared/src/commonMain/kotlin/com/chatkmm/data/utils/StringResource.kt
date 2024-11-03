package com.chatkmm.data.utils

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

expect fun StringResource.localize(): String
expect fun StringResource.format(value: String): String

fun StringResource.desc(): StringDesc {
    return this.localize().desc()
}