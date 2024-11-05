package com.chatkmm.features.splash.data

import com.chatkmm.data.model.ConfigParams
import com.chatkmm.data.model.CustomResponseException
import com.chatkmm.data.utils.Log
import com.chatkmm.data.utils.localize
import com.chatkmm.entity.DeadTokenException
import com.chatkmm.features.splash.domain.SplashRepository
import com.chatkmm.resources.MultiplatformResource
import dev.icerock.moko.network.generated.apis.UsersApi
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess

class SplashRepositoryImpl(private val usersApi: UsersApi, private val configParams: ConfigParams): SplashRepository {
    override suspend fun isAuthorized(): Boolean {
        try {
            val isHaveTokens = isHaveTokens()

            if (!isHaveTokens) {
                return false
            }

            val result = usersApi.checkJwtApiV1UsersCheckJwtGetResponse()

            return result.httpResponse.status.isSuccess()
        } catch (e: DeadTokenException) {
            e.printStackTrace()
            return false
        } catch (e: Throwable) {
            if (e is CustomResponseException && e.isAccessDenied) {
                return false
            }

            val text = if (e is CustomResponseException) {
                e.responseMessage.ifEmpty { MultiplatformResource.strings.errorDescription.localize() }
            } else {
                MultiplatformResource.strings.errorDescription.localize()
            }

            throw Throwable(message = text)
        }
    }

    private fun isHaveTokens(): Boolean {
        val keyValueStorage = configParams.keyValueStorage
        val accessToken = keyValueStorage.accessToken
        val refreshToken = keyValueStorage.refreshToken

        return (!accessToken.isNullOrEmpty() && !refreshToken.isNullOrEmpty())
    }
}