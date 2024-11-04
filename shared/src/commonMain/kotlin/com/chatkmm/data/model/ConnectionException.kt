package com.chatkmm.data.model

import com.chatkmm.domain.constants.Constants
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.network.sockets.SocketTimeoutException

fun isConnectionException(e: Throwable): Boolean {
    return when (e) {
        is ConnectTimeoutException,
        is HttpRequestTimeoutException,
        is SocketTimeoutException,
        -> true
        else -> false
    }
}