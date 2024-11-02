package com.chatkmm.data.utils

import com.chatkmm.entity.enums.ErrorType

expect fun Log(title: String, message: String, errorType: ErrorType = ErrorType.DISPLAY)
