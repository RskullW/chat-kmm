package com.chatkmm.base.features.enum

enum class Screen {
    SPLASH {
        override fun toString() = "splash"
    },
    AUTHORIZATION {
        override fun toString() = "authorization"
    },
    MAIN {
        override fun toString() = "main"
    };

    abstract override fun toString(): String
}
