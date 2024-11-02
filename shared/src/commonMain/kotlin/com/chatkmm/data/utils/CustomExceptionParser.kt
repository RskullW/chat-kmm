package com.chatkmm.data.utils

import com.chatkmm.data.model.CustomResponseException
import dev.icerock.moko.network.exceptionfactory.HttpExceptionFactory
import dev.icerock.moko.network.exceptions.ResponseException
import io.ktor.client.request.HttpRequest
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class CustomExceptionParser(private val json: Json) : HttpExceptionFactory.HttpExceptionParser {

    @Suppress("NestedBlockDepth")
    override fun parseException(
        request: HttpRequest,
        response: HttpResponse,
        responseBody: String?,
    ): ResponseException? {
        @Suppress("TooGenericExceptionCaught", "SwallowedException")
        return try {
            val body = responseBody.orEmpty()
            val jsonObject = json.parseToJsonElement(body).jsonObject
            val responseTitle = jsonObject.get(ERROR_TITLE)?.jsonPrimitive?.content.orEmpty()
            val responseMessage = jsonObject.get(ERROR_MESSAGE)?.jsonPrimitive?.content.orEmpty()
            val responseStatus: Int = jsonObject.get(ERROR_STATUS)?.jsonPrimitive?.int ?: 0
            CustomResponseException(
                request = request,
                response = response,
                responseTitle = responseTitle,
                responseMessage = responseMessage,
                responseStatus = responseStatus
            )
        } catch (exception: Exception) {
            null
        }
    }

    companion object {
        private const val ERROR_TITLE = "title"
        private const val ERROR_MESSAGE = "detail"
        private const val ERROR_STATUS = "status"
    }
}
