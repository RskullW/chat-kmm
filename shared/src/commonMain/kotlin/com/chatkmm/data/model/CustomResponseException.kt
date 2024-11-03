package com.chatkmm.data.model

import dev.icerock.moko.network.exceptions.ResponseException
import io.ktor.client.request.HttpRequest
import io.ktor.client.statement.HttpResponse

class CustomResponseException(
    request: HttpRequest,
    response: HttpResponse,
    responseMessage: String,
    val responseType: String,
    val responseStatus: Int
) : ResponseException(request, response, responseMessage) {
    @Suppress("MagicNumber")
    fun isServerError(): Boolean {
        return (responseStatus in 500..599)
    }
}

fun Exception.isServerError(): Boolean {
    return if (this is CustomResponseException) {
        this.isServerError()
    } else {
        false
    }
}
