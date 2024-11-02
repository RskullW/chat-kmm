package com.chatkmm.entity

class DeadTokenException(
    message: String,
    cause: Throwable
) : RuntimeException(message, cause)