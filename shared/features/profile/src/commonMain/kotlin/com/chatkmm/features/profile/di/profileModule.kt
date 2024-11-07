package com.chatkmm.features.profile.di

import com.chatkmm.features.profile.data.ProfileRepositoryImpl
import com.chatkmm.features.profile.domain.ProfileRepository
import com.chatkmm.features.profile.presentation.ProfileViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val profileModule: Module = module {
    factory <ProfileRepository> { ProfileRepositoryImpl(usersApi = get(), keyValueStorage = get()) }
    factory { ProfileViewModel(profileRepository = get()) }
}