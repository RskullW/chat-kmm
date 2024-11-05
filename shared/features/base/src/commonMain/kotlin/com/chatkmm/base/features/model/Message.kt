package com.chatkmm.base.features.model

data class Message(
    val message: String,
    val isMeMessage: Boolean,
    val dateFormatted: String,
)