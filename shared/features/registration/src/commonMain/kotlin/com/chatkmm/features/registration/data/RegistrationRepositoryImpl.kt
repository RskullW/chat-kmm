package com.chatkmm.features.registration.data

import com.chatkmm.data.infrastructure.KeyValueStorage
import com.chatkmm.features.registration.domain.RegistrationRepository
import dev.icerock.moko.network.generated.apis.AuthorizationApi

class RegistrationRepositoryImpl(private val authorizationApi: AuthorizationApi, private val keyValueStorage: KeyValueStorage): RegistrationRepository {
    override fun getPhoneNumber(): String {
        TODO("Not yet implemented")
    }

    override fun register(name: String, username: String): Boolean {
        TODO("Not yet implemented")
    }
}