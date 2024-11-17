package com.chatkmm.features.profile.domain

import com.chatkmm.base.features.enum.Zodiac
import dev.icerock.moko.network.generated.models.GetCurrentUserProfile
import dev.icerock.moko.network.generated.models.UserProfileSend

interface ProfileRepository {
    public suspend fun exitProfile(): Boolean
    public suspend fun getCurrentUser(): UserProfileSend
    public suspend fun getDateFormatted(birthday: String?): String?
    public suspend fun getAvatarUrl(): String?
    public suspend fun updateUser(
        name: String? = null,
        birthday: String? = null,
        city: String? = null,
        aboutMe: String? = null,
    )
    public suspend fun updateImage(
        fileName: String?,
        base64: String?
    )

    public fun getZodiacFromData(data: String): Zodiac?
}