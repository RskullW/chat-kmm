package com.chatkmm.features.authorization.data

import com.chatkmm.base.features.enum.Screen
import com.chatkmm.data.infrastructure.KeyValueStorage
import com.chatkmm.data.model.CustomResponseException
import com.chatkmm.data.utils.CustomExceptionParser
import com.chatkmm.data.utils.Log
import com.chatkmm.domain.constants.Constants
import com.chatkmm.entity.PhoneNumberCountry
import com.chatkmm.features.authorization.domain.AuthorizationRepository
import com.google.i18n.phonenumbers.PhoneNumberUtil
import dev.icerock.moko.network.generated.apis.AuthorizationApi
import dev.icerock.moko.network.generated.models.CheckAuthCode
import dev.icerock.moko.network.generated.models.PhoneBase
import java.util.Locale

actual class AuthorizationRepositoryImpl(private val authorizationApi: AuthorizationApi, private val keyValueStorage: KeyValueStorage): AuthorizationRepository {
    private val phoneNumberUtil = PhoneNumberUtil.getInstance()

    actual override fun getCountries(): List<PhoneNumberCountry> {
        return phoneNumberUtil.supportedRegions
            .filter { regionCode ->
                phoneNumberUtil.getCountryCodeForRegion(regionCode) != 0
            }
            .sorted()
            .map { regionCode ->
                val countryCode = phoneNumberUtil.getCountryCodeForRegion(regionCode).toString()
                PhoneNumberCountry(
                    country = getCountryName(regionCode),
                    region = regionCode,
                    regionCode = "+$countryCode"
                )
            }
    }

    actual override fun getMask(region: String): String {
        val exampleNumber = phoneNumberUtil.getExampleNumberForType(region, PhoneNumberUtil.PhoneNumberType.MOBILE)

        return if (exampleNumber != null) {
            val formattedExample = phoneNumberUtil.format(exampleNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
            formattedExample.replace(Regex("\\d"), "X")
        } else {
            Constants.Region.defaultMask
        }
    }

    actual override fun getCountryByPhone(phoneNumber: String): PhoneNumberCountry? {
        return try {
            val parsedNumber = phoneNumberUtil.parse(phoneNumber, null)
            val regionCode = phoneNumberUtil.getRegionCodeForNumber(parsedNumber)
            val countryCode = phoneNumberUtil.getCountryCodeForRegion(regionCode).toString()

            PhoneNumberCountry(
                country = getCountryName(regionCode),
                region = regionCode,
                regionCode = "+$countryCode"
            )
        } catch (e: Exception) {
            null
        }
    }

    actual override suspend fun sendCode(phoneNumber: String): Boolean {
        val result = authorizationApi.sendAuthCodeApiV1UsersSendAuthCodePost(
            phoneBase = PhoneBase(phone = phoneNumber)
        )

        return result.isSuccess
    }

    actual override suspend fun checkCode(phoneNumber: String, code: String): Screen {
        val result = authorizationApi.checkAuthCodeApiV1UsersCheckAuthCodePost(
            CheckAuthCode(
                phone = phoneNumber,
                code = code
            )
        )

        keyValueStorage.phoneNumber = phoneNumber

        return if (result.isUserExists) {
            keyValueStorage.accessToken = result.accessToken
            keyValueStorage.refreshToken = result.refreshToken

            Screen.MENU
        } else {
            Screen.REGISTRATION
        }
    }

    private fun getCountryName(regionCode: String): String {
        return Locale("", regionCode).displayCountry
    }
}