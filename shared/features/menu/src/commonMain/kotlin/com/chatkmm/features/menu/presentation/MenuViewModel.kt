package com.chatkmm.features.menu.presentation

import com.chatkmm.base.features.StateFlow
import com.chatkmm.base.features.ViewModel
import com.chatkmm.base.features.enum.Screen
import com.chatkmm.base.features.enum.StateScreen
import com.chatkmm.base.features.model.Chat
import com.chatkmm.data.model.CustomResponseException
import com.chatkmm.data.model.transferArguments
import com.chatkmm.data.utils.localize
import com.chatkmm.domain.constants.TransferArguments
import com.chatkmm.entity.DeadTokenException
import com.chatkmm.features.menu.domain.MenuRepository
import com.chatkmm.resources.MultiplatformResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class MenuViewModel(private val menuRepository: MenuRepository): ViewModel() {
    val errorText: StateFlow<String?> = StateFlow(null)
    val profileUrl: StateFlow<String?> = StateFlow(null)
    val chats: StateFlow<List<Chat>> = StateFlow(emptyList())
    val connectionStatus: StateFlow<String> = StateFlow(MultiplatformResource.strings.inputUsername.localize())
    val newScreen: StateFlow<Screen?> = StateFlow(null)
    val isDeadToken: StateFlow<Boolean> = StateFlow(false)

    var isClearScreen: Boolean = false
        private set

    init {
        update()
    }

    public fun saveChatLocal(chat: Chat) {
        transferArguments[TransferArguments.localChat] = chat
    }

    public fun updateErrorText(value: String? = null) {
        errorText.update(value = value)
    }

    public fun update() {
        updateErrorText()

        if (stateScreen.getValue() == StateScreen.LOADING) {
            return
        }

        connectionStatus.update(value = MultiplatformResource.strings.connection.localize())
        updateStateScreen(StateScreen.LOADING)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val chatsDto = menuRepository.getChats()
                val user = menuRepository.getUser()
                val avatarUrl = menuRepository.getAvatarUrl()

                withContextMain {
                    chats.update(value = menuRepository.getChats())
                    profileUrl.update(value = avatarUrl)
                    connectionStatus.update(value = MultiplatformResource.strings.chats.localize())

                    updateStateScreen(StateScreen.DEFAULT)
                }
            }
            catch (e: DeadTokenException) {
                withContextMain {
                    isDeadToken.update(true)
                }
            }
            catch (e: CustomResponseException) {
                withContextMain {
                    errorText.update(e.responseMessage)
                    updateStateScreen(StateScreen.DEFAULT)
                }
            } catch (e: Throwable) {
                withContextMain {
                    errorText.update(MultiplatformResource.strings.errorDescription.localize())
                    updateStateScreen(StateScreen.DEFAULT)
                }
            }
        }
    }

    public fun setScreen(screen: Screen, isClear: Boolean) {
        isClearScreen = isClear
        newScreen.update(value = screen)
    }
}