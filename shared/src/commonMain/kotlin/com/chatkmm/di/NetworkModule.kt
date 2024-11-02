package com.chatkmm.di

import com.chatkmm.data.infrastructure.KeyValueStorage
import com.chatkmm.data.infrastructure.NativeHost
import com.chatkmm.data.utils.CustomExceptionParser
import com.chatkmm.entity.DeadTokenException
import dev.icerock.moko.network.createHttpClientEngine
import dev.icerock.moko.network.exceptionfactory.HttpExceptionFactory
import dev.icerock.moko.network.exceptionfactory.parser.ValidationExceptionParser
import dev.icerock.moko.network.nullable.Nullable
import dev.icerock.moko.network.plugins.ExceptionPlugin
import dev.icerock.moko.network.plugins.RefreshTokenPlugin
import dev.icerock.moko.network.plugins.TokenPlugin
import io.ktor.client.HttpClient
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module


@Suppress("LongMethod")
val networkModule: Module = module {
    val baseUrl: String = NativeHost.getUrl()

    single<Json> {
        Json {
            ignoreUnknownKeys = true
        }
    }

    singleOf(::createHttpClient)

 /*   single<TestApi> {
        TestApi(
            basePath = baseUrl,
            httpClient = get(),
            json = get()
        )
    }*/

}

private fun createHttpClient(
    json: Json,
    keyValueStorage: KeyValueStorage,
//    tokenApi: TokenApi?,
): HttpClient {
    return HttpClient(createHttpClientEngine()) {

/*
        tokenApi?.let {
            install(RefreshTokenPlugin) {
                isCredentialsActual = { request ->
                    request.headers["Authorization"] == keyValueStorage.accessToken?.let { "Bearer $it" }
                }
                updateTokenHandler = {
                    try {
                        val response = tokenApi.refreshTokenPost(
                            RefreshTokenRequest(
                                accessToken = Nullable(keyValueStorage.accessToken),
                                refreshToken = Nullable(keyValueStorage.refreshToken)
                            )
                        )
                        val body = response.body()
                        keyValueStorage.accessToken = body.accessToken?.value
                        keyValueStorage.refreshToken = body.refreshToken?.value
                        response.httpResponse.status == HttpStatusCode.OK
                    } catch (exc: Exception) {
                        keyValueStorage.accessToken = null
                        keyValueStorage.refreshToken = null

                        throw DeadTokenException(
                            message = exc.message ?: "",
                            cause = exc
                        )
                    }
                }
            }
            install(TokenPlugin) {
                tokenHeaderName = "Authorization"
                tokenProvider = TokenPlugin.TokenProvider {
                    keyValueStorage.accessToken?.let { "Bearer $it" }
                }
            }
        }
*/

        install(ExceptionPlugin) {
            exceptionFactory = HttpExceptionFactory(
                defaultParser = CustomExceptionParser(json),
                customParsers = mapOf(
                    HttpStatusCode.UnprocessableEntity.value to ValidationExceptionParser(json)
                )
            )
        }

        expectSuccess = false
    }
}
