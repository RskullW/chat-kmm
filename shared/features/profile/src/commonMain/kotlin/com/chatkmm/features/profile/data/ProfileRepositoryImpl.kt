package com.chatkmm.features.profile.data

import com.chatkmm.base.features.enum.Zodiac
import com.chatkmm.features.profile.domain.ProfileRepository
import dev.icerock.moko.network.generated.apis.UsersApi
import dev.icerock.moko.network.generated.models.GetCurrentUserProfile
import dev.icerock.moko.network.generated.models.UploadImage
import dev.icerock.moko.network.generated.models.UserProfileSend
import dev.icerock.moko.network.generated.models.UserUpdate

class ProfileRepositoryImpl(private val usersApi: UsersApi): ProfileRepository {
    override suspend fun getCurrentUser(): UserProfileSend {
        return usersApi.getCurrentUserApiV1UsersMeGet().profileData
    }

    override suspend fun getDateFormatted(birthday: String?): String? {
        val parts = birthday?.split(".")

        if (parts?.size != 3) {
            return null
        }

        val year = parts[0]
        val month = parts[1]
        val day = parts[2]

        return "$day.$month.$year"
    }

    override suspend fun updateUser(
        name: String?,
        birthday: String?,
        city: String?,
        aboutMe: String?,
        fileName: String?,
        image: String?,
    ) {
        val currentUser = getCurrentUser()

        val name: String = if (name == currentUser.name) { currentUser.name } else { name ?: "" }
        val birthday = if (birthday == currentUser.birthday) { null } else { birthday }
        val city = if (city == currentUser.city) { null } else { city }
        val aboutMe = if (aboutMe == currentUser.status) { null } else { aboutMe }
        val uploadImage = if (fileName != null && image != null) {
            UploadImage(
                filename = fileName,
                base64 = image
            )
        } else {
            null
        }
        val userUpdate = UserUpdate(
            name = name,
            username = currentUser.username,
            birthday = birthday,
            city = city,
            status = aboutMe,
            avatar = uploadImage
        )

        usersApi.updateUserApiV1UsersMePut(userUpdate = userUpdate)
    }

    override fun getZodiacFromData(data: String): Zodiac? {
        val parts = data.split(".")

        if (parts.size != 3) {
            return null
        }

        val day: Int = parts[0].toInt()
        val month: Int = parts[1].toInt()

        return Zodiac.getZodiacSign(
            day = day,
            month = month
        )
    }
}