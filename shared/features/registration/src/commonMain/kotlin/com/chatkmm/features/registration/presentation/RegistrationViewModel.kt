package com.chatkmm.features.registration.presentation

import com.chatkmm.base.features.StateFlow
import com.chatkmm.base.features.ViewModel
import com.chatkmm.base.features.enum.Screen
import com.chatkmm.features.registration.domain.RegistrationRepository

class RegistrationViewModel(private val registrationRepository: RegistrationRepository): ViewModel() {
    val errorText: StateFlow<String?> = StateFlow(null)
    val errorTextInput: StateFlow<String?> = StateFlow(null)
    val isSuccess: StateFlow<Boolean> = StateFlow(false)

    val phoneNumber: String = registrationRepository.getPhoneNumber()

    init {

    }

    public fun register(name: String, username: String) {

    }

    public fun updateErrorText(text: String? = null) {
        errorText.update(text)
    }

    public fun updateErrorTextInput(text: String? = null) {
        errorTextInput.update(text)
    }
}