package com.chatkmm.features.profile.presentation

import com.chatkmm.base.features.StateFlow
import com.chatkmm.base.features.ViewModel
import com.chatkmm.base.features.enum.StateScreen
import com.chatkmm.base.features.enum.Zodiac
import com.chatkmm.data.model.CustomResponseException
import com.chatkmm.data.utils.Log
import com.chatkmm.data.utils.localize
import com.chatkmm.domain.constants.Constants
import com.chatkmm.entity.DeadTokenException
import com.chatkmm.features.profile.domain.ProfileRepository
import com.chatkmm.resources.MultiplatformResource
import dev.icerock.moko.network.exceptions.ResponseException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlin.io.encoding.Base64

class ProfileViewModel(
    private val profileRepository: ProfileRepository
): ViewModel() {
    val userName: StateFlow<String> = StateFlow("")
    val phoneNumber: StateFlow<String> = StateFlow("")
    val zodiac: StateFlow<Zodiac?> = StateFlow(null)
    val oldName: StateFlow<String?> = StateFlow(null)
    val oldBirthday: StateFlow<String?> = StateFlow(null)
    val oldCity: StateFlow<String?> = StateFlow(null)
    val oldAboutMe: StateFlow<String?> = StateFlow(null)
    val oldProfileImage: StateFlow<String?> = StateFlow(null)
    val isEnabledButton: StateFlow<Boolean> = StateFlow(false)

    val errorText: StateFlow<String?> = StateFlow(null)
    val isDeadToken: StateFlow<Boolean?> = StateFlow(null)
    val isSaved: StateFlow<Boolean> = StateFlow(false)

    init {
        update()
    }

    public fun update(isLoading: Boolean = true) {
        isSaved.update(false)
        isEnabledButton.update(false)
        updateErrorText()

        if (isLoading && stateScreen.getValue() == StateScreen.LOADING) {
            return
        }

        if (isLoading) {
            updateStateScreen(StateScreen.LOADING)
        }

        CoroutineScope(Dispatchers.IO).launch {
             try {
                 val user = profileRepository.getCurrentUser()
                 val birthday: String? = user.birthday

                 val birthdayFormatted = profileRepository.getDateFormatted(birthday)

                 withContextMain {
                     oldName.update(user.name)
                     oldCity.update(user.city)
                     oldBirthday.update(birthdayFormatted)
                     oldAboutMe.update(user.status)
                     oldProfileImage.update(user.avatar)
                     userName.update(user.username)
                     phoneNumber.update(user.phone)

                     if (birthdayFormatted != null) {
                         zodiac.update(profileRepository.getZodiacFromData(data = birthdayFormatted))
                     } else {
                         zodiac.update(null)
                     }

                     updateStateScreen(StateScreen.DEFAULT)
                 }
             } catch (e: DeadTokenException) {
                 e.printStackTrace()

                 withContextMain {
                     isDeadToken.update(true)
                     updateStateScreen(StateScreen.DEFAULT)
                 }
             } catch (e: CustomResponseException) {
                 e.printStackTrace()

                 withContextMain {
                     errorText.update(e.responseMessage.ifEmpty { MultiplatformResource.strings.errorDescription.localize() })
                     updateStateScreen(StateScreen.DEFAULT)
                 }
             } catch (e: Throwable) {
                 withContextMain {
                     errorText.update(e.message?.ifEmpty { MultiplatformResource.strings.errorDescription.localize() })
                     updateStateScreen(StateScreen.DEFAULT)
                 }
             }
        }
    }

    public fun updateErrorText(value: String? = null) {
        errorText.update(value = value)
    }

    public fun afterTextChangedName(newValue: String): String {
        setEnabledButton(name = newValue)

        return if (newValue.length > Constants.Numbers.nameLength) {
            newValue.dropLast(1)
        } else {
            newValue
        }
    }

    public fun afterTextChangedCity(newValue: String): String {
        setEnabledButton(city = newValue)

        return if (newValue.length > Constants.Numbers.cityLength) {
            newValue.dropLast(1)
        } else {
            newValue
        }
    }

    public fun afterTextChangedBirthday(newValue: String): String {
        val mask = "XX.XX.XXXX"
        val digits = newValue.filter { it.isDigit() }
        val formattedDate = StringBuilder()

        var digitIndex = 0

        if (mask.length < newValue.length) {
            return newValue.substring(mask.length)
        }

        for (char in mask) {
            if (char == 'X') {
                if (digitIndex < digits.length) {
                    formattedDate.append(digits[digitIndex])
                    digitIndex++
                } else {
                    break
                }
            } else {
                formattedDate.append(char)
            }
        }

        setEnabledButton(birthday = formattedDate.toString())

        return formattedDate.toString()
    }

    public fun afterTextChangedAboutMe(newValue: String): String {
        setEnabledButton(aboutMe = newValue)

        return newValue
    }

    public fun save(
        name: String?,
        birthday: String?,
        city: String?,
        aboutMe: String?
    ) {
        if (stateScreen.getValue() == StateScreen.LOADING) {
            return
        }

        updateStateScreen(StateScreen.LOADING)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                withContextMain {
                    profileRepository.updateUser(
                        name = name,
                        birthday = birthday,
                        city = city,
                        aboutMe = aboutMe,
                        fileName = null,
                        image = null
                    )
                    isSaved.update(true)
                }
            } catch (e: DeadTokenException) {
                e.printStackTrace()

                withContextMain {
                    isDeadToken.update(true)
                    updateStateScreen(StateScreen.DEFAULT)
                }
            } catch (e: ResponseException) {
                e.printStackTrace()

                withContextMain {
                    errorText.update(e.responseMessage.ifEmpty { MultiplatformResource.strings.errorDescription.localize() })
                    updateStateScreen(StateScreen.DEFAULT)
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                withContextMain {
                    errorText.update(e.message?.ifEmpty { MultiplatformResource.strings.errorDescription.localize() })
                    updateStateScreen(StateScreen.DEFAULT)
                }
            }
        }
    }

    public fun setImage(
        fileName: String,
        base64: String,
    ) {

    }

    private fun setEnabledButton(
        name: String? = null,
        birthday: String? = null,
        city: String? = null,
        aboutMe: String? = null
    ) {
        val enabled =
                (name != oldName.getValue()) ||
                (birthday != oldBirthday.getValue()) ||
                (city != oldCity.getValue()) ||
                (aboutMe != oldAboutMe.getValue())

        isEnabledButton.update(enabled)
    }
}