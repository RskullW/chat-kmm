package com.chatkmm.base.features

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StateFlow <T> (default: T) {
    private val _something: MutableStateFlow<T> = MutableStateFlow(default)
    val state: StateFlow<T> = _something

    fun update(value: T) {
        _something.value = value
    }

    fun getValue(): T {
        return state.value
    }

    fun addObserver(observer: (T) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            state.collect { value ->
                observer(value)
            }
        }
    }
}
