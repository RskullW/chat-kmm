package com.chatkmm.base.features.enum

enum class Screen {
    SPLASH {
        override fun toString() = "splash"
    },
    AUTHORIZATION {
        override fun toString() = "authorization"
    },
    REGISTRATION {
        override fun toString() = "registration"
    },
    PROFILE {
        override fun toString() = "profile"
    },

    CHAT {
        override fun toString() = "chat"
    },
    MENU {
        override fun toString() = "menu"
    };

    abstract override fun toString(): String
}
