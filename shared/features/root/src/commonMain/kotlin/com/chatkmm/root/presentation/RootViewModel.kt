package com.chatkmm.root.presentation

import com.chatkmm.base.features.StateFlow
import com.chatkmm.base.features.ViewModel
import com.chatkmm.base.features.enum.Screen

class RootViewModel: ViewModel() {
    val screen: StateFlow<Screen?> = StateFlow(null)
    var arguments: List<String> = emptyList()
        private set
    var isClearStack: Boolean = false
        private set

    private val openScreens: MutableList<Screen> = mutableListOf()

    public fun updateScreen(screen: Screen, argumentsJson: List<String> = emptyList(), isClear: Boolean) {
        val currentScreen = this.screen.getValue()

        if (screen == currentScreen) {
            return
        }

        isClearStack = isClear

        if (isClear) {
            openScreens.clear()
        }

        openScreens.add(screen)

        arguments = argumentsJson
        this.screen.update(screen)
    }

    public fun finishScreen() {
        this.screen.update(null)
    }

    public fun removeLast() {
        openScreens.removeLast()
    }

    fun areThereOtherOpenScreens(): Boolean {
        return openScreens.size > 1
    }
}