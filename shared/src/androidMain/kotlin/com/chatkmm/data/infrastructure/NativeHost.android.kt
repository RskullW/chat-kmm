package com.chatkmm.data.infrastructure

private external fun url(): String

actual object NativeHost {
    init {
        System.loadLibrary("api-keys")
    }

    actual fun getUrl(): String {
        return url()
    }
}