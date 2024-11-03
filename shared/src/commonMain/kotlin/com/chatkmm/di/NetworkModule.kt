package com.chatkmm.di

import com.chatkmm.data.infrastructure.KeyValueStorage
import com.chatkmm.data.infrastructure.NativeHost
import com.chatkmm.data.model.CustomResponseException
import com.chatkmm.data.utils.CustomExceptionParser
import com.chatkmm.data.utils.Log
import com.chatkmm.entity.DeadTokenException
import dev.icerock.moko.network.createHttpClientEngine
import dev.icerock.moko.network.exceptionfactory.HttpExceptionFactory
import dev.icerock.moko.network.exceptionfactory.parser.ValidationExceptionParser
import dev.icerock.moko.network.generated.apis.AuthorizationApi
import dev.icerock.moko.network.generated.apis.UsersApi
import dev.icerock.moko.network.generated.models.RefreshToken
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
import org.koin.mp.KoinPlatform


@Suppress("LongMethod")
val networkModule: Module = module {
    val baseUrl: String = NativeHost.getUrl()

    single<Json> {
        Json {
            ignoreUnknownKeys = true
        }
    }

    singleOf(::createHttpClient)

    single<UsersApi> {
        UsersApi(
            basePath = baseUrl,
            httpClient = get(),
            json = get()
        )
    }

}

private fun createHttpClient(
    json: Json,
    authorizationApi: AuthorizationApi?,
): HttpClient {
    val keyValueStorage: KeyValueStorage = KoinPlatform.getKoin().get()
    Log("ACCESS TOKEN", keyValueStorage.accessToken.toString())
    Log("REFRESH TOKEN", keyValueStorage.refreshToken.toString())

    return HttpClient(createHttpClientEngine()) {
        install(ExceptionPlugin) {
            exceptionFactory = HttpExceptionFactory(
                defaultParser = CustomExceptionParser(json),
                customParsers = mapOf(
                    HttpStatusCode.UnprocessableEntity.value to ValidationExceptionParser(json)
                )
            )
        }

        expectSuccess = false

        if (authorizationApi != null) {
            install(RefreshTokenPlugin) {
                isCredentialsActual = { request ->
                    request.headers["Authorization"] == keyValueStorage.accessToken?.let { "Bearer $it" }
                }
                updateTokenHandler = {
                    try {
                        val response = authorizationApi.refreshTokenApiV1UsersRefreshTokenPostResponse(
                            refreshToken = RefreshToken(refreshToken = keyValueStorage.refreshToken)
                        )

                        val body = response.body()
                        keyValueStorage.accessToken = body.accessToken
                        keyValueStorage.refreshToken = body.refreshToken

                        response.httpResponse.status == HttpStatusCode.OK
                    } catch (e: CustomResponseException) {
                        e.printStackTrace()

                        keyValueStorage.accessToken = null
                        keyValueStorage.refreshToken = null

                        throw DeadTokenException(
                            message = e.message ?: "",
                            cause = e
                        )
                    } catch (exc: Exception) {
                        exc.printStackTrace()

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
    }
}

