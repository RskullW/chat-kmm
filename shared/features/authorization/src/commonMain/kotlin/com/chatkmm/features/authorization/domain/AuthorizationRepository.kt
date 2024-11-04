package com.chatkmm.features.authorization.domain

import com.chatkmm.base.features.enum.Screen
import com.chatkmm.entity.PhoneNumberCountry

interface AuthorizationRepository {
    public fun getCountries(): List<PhoneNumberCountry>
    public fun getMask(region: String): String
    public fun getCountryByPhone(phoneNumber: String): PhoneNumberCountry?
    public suspend fun sendCode(phoneNumber: String): Boolean
    public suspend fun checkCode(phoneNumber: String, code: String): Screen
}