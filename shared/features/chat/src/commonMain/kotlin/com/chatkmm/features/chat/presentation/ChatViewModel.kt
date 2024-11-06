package com.chatkmm.features.chat.presentation

import com.chatkmm.base.features.StateFlow
import com.chatkmm.base.features.ViewModel
import com.chatkmm.base.features.enum.StateScreen
import com.chatkmm.base.features.model.Message
import com.chatkmm.data.model.CustomResponseException
import com.chatkmm.data.utils.localize
import com.chatkmm.entity.DeadTokenException
import com.chatkmm.features.chat.domain.ChatRepository
import com.chatkmm.resources.MultiplatformResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class ChatViewModel(private val chatRepository: ChatRepository): ViewModel() {
    val chatName: StateFlow<String> = StateFlow("")
    val profileImageUrl: StateFlow<String> = StateFlow("")
    val messages: StateFlow<List<Message>> = StateFlow(listOf())

    val errorText: StateFlow<String?> = StateFlow(null)
    val isDeadToken: StateFlow<Boolean?> = StateFlow(null)

    init {
        update()
    }

    public fun update() {
        updateErrorText()

        if (stateScreen.getValue() == StateScreen.LOADING) {
            return
        }

        updateStateScreen(StateScreen.LOADING)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val messages = chatRepository.getMessages()
                val profileImageUrl = chatRepository.getProfileUrl()
                val chatName = chatRepository.getName()

                withContextMain {
                    this@ChatViewModel.messages.update(value = messages)
                    this@ChatViewModel.profileImageUrl.update(value = profileImageUrl)
                    this@ChatViewModel.chatName.update(value = chatName)

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
}