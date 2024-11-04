package com.chatkmm.features.authorization.data

import com.chatkmm.base.features.enum.Screen
import com.chatkmm.entity.PhoneNumberCountry
import com.chatkmm.features.authorization.domain.AuthorizationRepository

expect class AuthorizationRepositoryImpl: AuthorizationRepository {
    override fun getCountries(): List<PhoneNumberCountry>
    override fun getMask(region: String): String
    override fun getCountryByPhone(phoneNumber: String): PhoneNumberCountry?
    override suspend fun sendCode(phoneNumber: String): Boolean
    override suspend fun checkCode(phoneNumber: String, code: String): Screen
}