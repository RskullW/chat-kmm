package com.chatkmm.data.utils

import com.chatkmm.data.model.CustomResponseException
import dev.icerock.moko.network.exceptionfactory.HttpExceptionFactory
import dev.icerock.moko.network.exceptions.ResponseException
import io.ktor.client.request.HttpRequest
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class CustomExceptionParser(private val json: Json) : HttpExceptionFactory.HttpExceptionParser {

    @Suppress("NestedBlockDepth")
    override fun parseException(
        request: HttpRequest,
        response: HttpResponse,
        responseBody: String?,
    ): CustomResponseException? {
        @Suppress("TooGenericExceptionCaught", "SwallowedException")
        return try {
            val body = responseBody.orEmpty()
            val jsonObject = json.parseToJsonElement(body).jsonObject

            val detail = jsonObject[ERROR] as? JsonObject ?: return null
            val msg = detail[ERROR_MESSAGE]?.jsonPrimitive?.content ?: "unknown error"
            val type = detail[ERROR_TYPE]?.jsonPrimitive?.content ?: "unknown type"
            val status = response.status.value

            CustomResponseException(
                request = request,
                response = response,
                responseType = type,
                responseMessage = msg,
                responseStatus = status
            )
        } catch (exception: Exception) {
            null
        }
    }

    companion object {
        private const val ERROR = "detail"
        private const val ERROR_MESSAGE = "msg"
        private const val ERROR_TYPE = "type"
    }
}
