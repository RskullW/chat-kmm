package com.chatkmm.features.registration.domain

interface RegistrationRepository {
    public fun getPhoneNumber(): String
    public fun register(name: String, username: String): Boolean
}