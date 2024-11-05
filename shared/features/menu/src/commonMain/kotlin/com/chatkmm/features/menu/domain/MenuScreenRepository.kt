package com.chatkmm.features.menu.domain

import com.chatkmm.base.features.model.Chat
import dev.icerock.moko.network.generated.models.GetCurrentUserProfile

// TODO: RoomDB - set 'interface' to 'expect' || SQLDelight - roomdb
interface MenuRepository {
    suspend fun getChats(): List<Chat> // TODO: Connect to Room DB / SQLDelight for KMM
    suspend fun getUser(): GetCurrentUserProfile
}