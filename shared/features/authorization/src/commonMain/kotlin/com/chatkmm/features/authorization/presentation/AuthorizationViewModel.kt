package com.chatkmm.features.authorization.presentation

import com.chatkmm.base.features.StateFlow
import com.chatkmm.base.features.ViewModel
import com.chatkmm.base.features.enum.Screen
import com.chatkmm.base.features.enum.StateScreen
import com.chatkmm.data.model.CustomResponseException
import com.chatkmm.data.utils.Log
import com.chatkmm.data.utils.localize
import com.chatkmm.domain.constants.Constants
import com.chatkmm.entity.PhoneNumberCountry
import com.chatkmm.features.authorization.domain.AuthorizationRepository
import com.chatkmm.resources.MultiplatformResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class AuthorizationViewModel(private val authorizationRepository: AuthorizationRepository): ViewModel() {
    val errorText: StateFlow<String?> = StateFlow(null)
    val currentRegion: StateFlow<PhoneNumberCountry> = StateFlow(PhoneNumberCountry())
    val countries: StateFlow<List<PhoneNumberCountry>> = StateFlow(emptyList())

    val newScreen: StateFlow<Screen?> = StateFlow(null)

    val currentPage: StateFlow<Int> = StateFlow(1)

    private var phoneNumber: String = ""
    private var code: String = ""

    val numberOfPages: Int = Constants.Numbers.numberOfPagesAuthorization

    init {
        update()
    }

    fun update() {
        if (stateScreen.getValue() == StateScreen.LOADING) {
            return
        }

        updateStateScreen(StateScreen.LOADING)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val allCountries = authorizationRepository.getCountries()
                val first = allCountries.firstOrNull()

                if (first != null) {
                    currentRegion.update(value = first)
                }

                countries.update(value = allCountries)

                updateStateScreen(StateScreen.DEFAULT)
            } catch (e: Throwable) {
                e.printStackTrace()
                updateStateScreen(StateScreen.DEFAULT)
                errorText.update(MultiplatformResource.strings.errorTitle.localize())
            }
        }
    }

    fun afterTextChanged(phoneNumber: String): String {
        val phoneNumberCountry = authorizationRepository.getCountryByPhone(phoneNumber = "$phoneNumber")

        if (phoneNumberCountry != null) {
            currentRegion.update(phoneNumberCountry )
        }

        val mask = authorizationRepository.getMask(currentRegion.getValue().region)

        var digits = phoneNumber.filter { it.isDigit() }
        val maskedNumber = StringBuilder()

        var digitIndex = 0

        if (mask.length < phoneNumber.length) {
            return this.phoneNumber.dropLast(1)
        }

        for (char in mask) {
            if (char == 'X') {
                if (digitIndex < digits.length) {
                    maskedNumber.append(digits[digitIndex])
                    digitIndex++
                } else {
                    break
                }
            } else {
                maskedNumber.append(char)
            }
        }

        updatePhoneNumber(maskedNumber.toString())
        return maskedNumber.toString()
    }
    fun afterTextCode(code: String): String {
        if (Constants.Numbers.codeLength < code.length) {
            return this.code.dropLast(1)
        }

        updateCode(code)
        return code
    }

    fun updatePage(newValue: Int) {
        if (currentPage.getValue() == 1 && newValue == 2) {
            sendCode(newValue)
        } else if (currentPage.getValue() == 2 && newValue == 3 && code.length == Constants.Numbers.codeLength) {
            checkCode()
        }
        else {
            currentPage.update(newValue.coerceIn(1..2))
        }
    }

    fun updatePhoneNumber(value: String) {
        phoneNumber = value
    }

    fun updateCode(value: String) {
        code = value
    }

    fun updateCountry(newCountry: PhoneNumberCountry) {
        currentRegion.update(newCountry)
    }

    fun updateErrorText(value: String? = null) {
        errorText.update(value = value)
    }

    private fun sendCode(newValue: Int) {
        if (stateScreen.getValue() == StateScreen.LOADING) {
            return
        }

        updateStateScreen(StateScreen.LOADING)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = authorizationRepository.sendCode(phoneNumber = phoneNumber)

                withContextMain {
                    if (result) {
                        currentPage.update(newValue)
                    } else {
                        errorText.update(MultiplatformResource.strings.errorTitle.localize())
                    }

                    updateStateScreen(StateScreen.DEFAULT)
                }
            }
            catch (e: CustomResponseException) {
                withContextMain {
                    e.printStackTrace()
                    updateStateScreen(StateScreen.DEFAULT)
                    errorText.update(e.responseMessage)
                }
            }
            catch (e: Throwable) {
                withContextMain {
                    e.printStackTrace()
                    updateStateScreen(StateScreen.DEFAULT)
                    errorText.update(MultiplatformResource.strings.errorDescription.localize())
                }
            }
        }
    }
    private fun checkCode() {
        if (stateScreen.getValue() == StateScreen.LOADING) {
            return
        }

        updateStateScreen(StateScreen.LOADING)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = authorizationRepository.checkCode(phoneNumber = phoneNumber, code = code)

                withContextMain {
                    newScreen.update(result)
                    updateStateScreen(StateScreen.DEFAULT)
                }
            }
            catch (e: CustomResponseException) {
                withContextMain {
                    e.printStackTrace()
                    updateStateScreen(StateScreen.DEFAULT)
                    errorText.update(e.responseMessage)
                }
            }
            catch (e: Throwable) {
                withContextMain {
                    e.printStackTrace()
                    updateStateScreen(StateScreen.DEFAULT)
                    errorText.update(MultiplatformResource.strings.errorDescription.localize())
                }
            }
        }
    }

}