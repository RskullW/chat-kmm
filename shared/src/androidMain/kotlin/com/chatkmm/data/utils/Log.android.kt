package com.chatkmm.data.utils

import com.chatkmm.entity.enums.ErrorType

actual fun Log(
    title: String,
    message: String,
    errorType: ErrorType
) {
    when(errorType) {
        ErrorType.WARNING -> android.util.Log.w(title, message)
        ErrorType.ERROR -> android.util.Log.e(title, message)
        else -> android.util.Log.d(title, message)
    }
}