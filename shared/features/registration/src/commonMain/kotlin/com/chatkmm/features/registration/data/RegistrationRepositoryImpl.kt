package com.chatkmm.features.registration.data

import com.chatkmm.data.infrastructure.KeyValueStorage
import com.chatkmm.data.utils.localize
import com.chatkmm.features.registration.domain.RegistrationRepository
import com.chatkmm.resources.MultiplatformResource
import dev.icerock.moko.network.generated.apis.AuthorizationApi
import dev.icerock.moko.network.generated.models.RegisterIn

class RegistrationRepositoryImpl(private val authorizationApi: AuthorizationApi, private val keyValueStorage: KeyValueStorage): RegistrationRepository {
    override fun getPhoneNumber(): String {
        return keyValueStorage.phoneNumber ?: ""
    }

    override suspend fun register(name: String, username: String): Boolean {
        val nameRegex = Regex("^[^\\d\\W_]{4,16}$")
        val usernameRegex = Regex("^[a-zA-Z0-9_-]{4,16}$")

        if (!nameRegex.matches(name)) {
            throw Throwable(message = MultiplatformResource.strings.joinErrorName.localize())
        }

        if (!usernameRegex.matches(username)) {
            throw Throwable(message = MultiplatformResource.strings.joinErrorUsername.localize())
        }

        val token = authorizationApi.userRegisterApiV1UsersRegisterPost(
            RegisterIn(
                phone = keyValueStorage.phoneNumber ?: "",
                name = name,
                username = username,
            )
        )

        keyValueStorage.accessToken = token.accessToken
        keyValueStorage.refreshToken = token.refreshToken

        return true
    }
}