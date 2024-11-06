package com.chatkmm.base.features.enum

enum class Zodiac {
    ARIES, TAURUS, GEMINI, CANCER, LEO, VIRGO, LIBRA, SCORPIO, SAGITTARIUS, CAPRICORN, AQUARIUS, PISCES;

    companion object {
        fun getZodiacSign(day: Int, month: Int): Zodiac {
            return when {
                (month == 3 && day >= 21) || (month == 4 && day <= 19) -> ARIES
                (month == 4 && day >= 20) || (month == 5 && day <= 20) -> TAURUS
                (month == 5 && day >= 21) || (month == 6 && day <= 20) -> GEMINI
                (month == 6 && day >= 21) || (month == 7 && day <= 22) -> CANCER
                (month == 7 && day >= 23) || (month == 8 && day <= 22) -> LEO
                (month == 8 && day >= 23) || (month == 9 && day <= 22) -> VIRGO
                (month == 9 && day >= 23) || (month == 10 && day <= 22) -> LIBRA
                (month == 10 && day >= 23) || (month == 11 && day <= 21) -> SCORPIO
                (month == 11 && day >= 22) || (month == 12 && day <= 21) -> SAGITTARIUS
                (month == 12 && day >= 22) || (month == 1 && day <= 19) -> CAPRICORN
                (month == 1 && day >= 20) || (month == 2 && day <= 18) -> AQUARIUS
                (month == 2 && day >= 19) || (month == 3 && day <= 20) -> PISCES
                else -> throw IllegalArgumentException("Zodiac not founded")
            }
        }
    }
}