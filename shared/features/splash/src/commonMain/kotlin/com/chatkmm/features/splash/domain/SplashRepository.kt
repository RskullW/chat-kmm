package com.chatkmm.features.splash.domain

interface SplashRepository {
    public suspend fun isAuthorized(): Boolean
}