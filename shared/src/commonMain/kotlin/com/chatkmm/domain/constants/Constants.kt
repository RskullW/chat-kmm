package com.chatkmm.domain.constants

object Constants {

    object Numbers {
        val SECOND = 1000L
        val MINUTE = SECOND * 60

        val splashDelay = SECOND * 4
        val numberOfPagesAuthorization = 2
        val codeLength = 6
        val cityLength = 16
        val nameLength = 16
    }

    object Strings {
        val defaultRegion = "RU"

        object SYSTEM {
        }
    }

    object Currency {

    }
    object Region {
        val defaultMask = "+7 XXX XXX XX XX" // возвращаем дефолтную маску, если пример недоступен
    }

    object RequestCode {
    }

}