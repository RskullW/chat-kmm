package com.posledam.mapping

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc

fun StringResource.toStringDesc(): StringDesc {
    return StringDesc.Resource(this)
}