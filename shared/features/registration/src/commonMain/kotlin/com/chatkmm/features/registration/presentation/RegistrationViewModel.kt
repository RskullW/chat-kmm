package com.chatkmm.features.registration.presentation

import com.chatkmm.base.features.StateFlow
import com.chatkmm.base.features.ViewModel
import com.chatkmm.base.features.enum.Screen
import com.chatkmm.base.features.enum.StateScreen
import com.chatkmm.data.model.CustomResponseException
import com.chatkmm.data.utils.localize
import com.chatkmm.features.registration.domain.RegistrationRepository
import com.chatkmm.resources.MultiplatformResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class RegistrationViewModel(private val registrationRepository: RegistrationRepository): ViewModel() {
    val errorText: StateFlow<String?> = StateFlow(null)
    val isSuccess: StateFlow<Boolean> = StateFlow(false)

    val phoneNumber: String = registrationRepository.getPhoneNumber()

    public fun register(name: String, username: String) {
        if (stateScreen.getValue() == StateScreen.LOADING) {
            return
        }

        updateStateScreen(StateScreen.LOADING)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = registrationRepository.register(name = name, username = username)

                withContextMain {
                    if (result) {
                        isSuccess.update(true)
                        updateStateScreen(StateScreen.DEFAULT)
                    }
                }
            } catch (e: CustomResponseException) {
                e.printStackTrace()

                withContextMain {
                    errorText.update(value = e.responseMessage.ifEmpty { MultiplatformResource.strings.joinError.localize() } )
                    updateStateScreen(StateScreen.DEFAULT)

                }
            } catch (e: Throwable) {
                e.printStackTrace()

                withContextMain {
                    errorText.update(e.message)
                    updateStateScreen(StateScreen.DEFAULT)
                }
            }
        }
    }

    public fun updateErrorText(text: String? = null) {
        errorText.update(text)
    }
}