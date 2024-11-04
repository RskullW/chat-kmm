package com.chatkmm.features.authorization.data

import com.chatkmm.base.features.enum.Screen
import com.chatkmm.entity.PhoneNumberCountry
import com.chatkmm.features.authorization.domain.AuthorizationRepository

actual class AuthorizationRepositoryImpl : AuthorizationRepository {
    actual override fun getCountries(): List<PhoneNumberCountry> {
        TODO("Not yet implemented")
    }

    actual override fun getMask(region: String): String {
        TODO("Not yet implemented")
    }

    actual override fun getCountryByPhone(phoneNumber: String): PhoneNumberCountry? {
        TODO("Not yet implemented")
    }

    actual override suspend fun sendCode(phoneNumber: String): Boolean {
        TODO("Not yet implemented")
    }

    actual override suspend fun checkCode(phoneNumber: String, code: String): Screen {
        TODO("Not yet implemented")
    }
}