package com.chatkmm.ui

import android.content.Context
import android.graphics.Typeface
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.chatkmm.resources.MultiplatformResource

@Composable
fun MainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        Colors.Dark(LocalContext.current)
    } else {
        Colors.Light(LocalContext.current)
    }

    CompositionLocalProvider(
        localColors(LocalContext.current) provides colors,
        localTypography() provides Typography(regular(), bold())
    ) {
        MaterialTheme(
            content = content,
        )
    }
}

fun regular() = FontFamily( Font(resId = MultiplatformResource.fonts.roboto_regular.fontResourceId) )
fun bold() = FontFamily( Font(resId = MultiplatformResource.fonts.roboto_regular.fontResourceId) )

fun localColors(context: Context) = compositionLocalOf <Colors> { Colors.Light(context) }
fun localTypography() = compositionLocalOf { Typography(mainTextFont()) }

object B {
    @Composable
    fun colors(): Colors {
        return localColors(context = LocalContext.current).current
    }

    @Composable
    fun typography(): Typography {
        return localTypography().current
    }
}