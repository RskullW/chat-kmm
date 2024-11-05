package com.chatkmm.features.menu.data

import com.chatkmm.base.features.enum.StatusMessage
import com.chatkmm.base.features.model.Chat
import com.chatkmm.base.features.model.Message
import com.chatkmm.features.menu.domain.MenuRepository
import dev.icerock.moko.network.generated.apis.UsersApi
import dev.icerock.moko.network.generated.models.GetCurrentUserProfile
import kotlinx.coroutines.delay

class MenuRepositoryImpl(private val usersApi: UsersApi): MenuRepository {

    // TODO: Connect to Room DB / SQLDelight for KMM
    override suspend fun getChats(): List<Chat> {
        val chats = mutableListOf<Chat>()

        chats.apply {
            repeat(3) { index ->
                add(
                    Chat(
                        imageUrl = "",
                        name = "Игорь_$index",
                        messages = generateMessage(";-)"),
                        status = StatusMessage.SENT,
                        newMessage = null,
                    )
                )
            }
            repeat(3) { index ->
                add(
                    Chat(
                        imageUrl = "https://i.ytimg.com/vi/VZt6HkKAbPA/maxresdefault.jpg",
                        name = "Галь Гадот_$index",
                        messages = generateMessage("Мне уже этот мир абсолютно понятен, и я здесь ищу только одного: покоя, умиротворения и вот этой гармонии от слияния с бесконечно вечным"),
                        status = StatusMessage.READ,
                        newMessage = index,
                    )
                )
            }

            repeat(3) { index ->
                add(
                    Chat(
                        imageUrl = "https://i.pinimg.com/736x/7e/15/1e/7e151e3bbbd0572d3724aa11b6755302.jpg",
                        name = "Гро\$ный_$index",
                        messages = generateMessage("- Назовите ваши сильные стороны. - Настойчивость. - Спасибо, мы с вами свяжемся. - Я подожду здесь"),
                        status = StatusMessage.SENDING,
                        newMessage = index,
                    )
                )
            }

            repeat(3) { index ->
                add(
                    Chat(
                        imageUrl = "https://a.d-cd.net/bf4aca9s-1920.jpg",
                        name = "GoouseBody_$index",
                        messages = generateMessage("Эй, чувак. Возьми трубку. Чуваааааак! Чуваааааак"),
                        status = StatusMessage.NOT_SENT,
                        newMessage = null,
                    )
                )
            }
        }

        return chats
    }

    override suspend fun getUser(): GetCurrentUserProfile {
        delay(2000)
        return usersApi.getCurrentUserApiV1UsersMeGet()
    }

    private fun generateMessage(lastMessage: String): List<Message> {
        val messages = mutableListOf<Message>(
            Message(
                message = "Привет!",
                isMeMessage = false,
                dateFormatted = "10:01",
            ),
            Message(
                message = "Хай, how are you?",
                isMeMessage = true,
                dateFormatted = "10:11",
            ),
            Message(
                message = "O my god, you инглиш? ",
                isMeMessage = false,
                dateFormatted = "12:28",
            ),
            Message(
                message = lastMessage,
                isMeMessage = true,
                dateFormatted = "13:37",
            )
        )

        return messages
    }
}