package com.chatkmm.ui

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.chatkmm.resources.MultiplatformResource

sealed class Colors {
    abstract val primary: Color
    abstract val secondary: Color
    abstract val thirdly: Color
    abstract val white: Color
    abstract val black: Color
    abstract val error: Color
    abstract val border: Color
    abstract val red: Color
    abstract val gray: Color
    abstract val transparent: Color

    data class Light(
        private val context: Context,
        override val primary: Color = Color(MultiplatformResource.colors.primaryColor.getColor(context)),
        override val secondary: Color = Color(MultiplatformResource.colors.secondaryColor.getColor(context)),
        override val thirdly: Color = Color(MultiplatformResource.colors.thirdlyColor.getColor(context)),
        override val white: Color = Color(MultiplatformResource.colors.white.getColor(context)),
        override val black: Color = Color(MultiplatformResource.colors.black.getColor(context)),
        override val error: Color = Color(MultiplatformResource.colors.errorColor.getColor(context)),
        override val border: Color = Color(MultiplatformResource.colors.borderColor.getColor(context)),
        override val red: Color = Color(MultiplatformResource.colors.red.getColor(context)),
        override val gray: Color = Color(MultiplatformResource.colors.gray.getColor(context)),
        override val transparent: Color = Color.Transparent,
    ): Colors()

    data class Dark(
        private val context: Context,
        override val primary: Color = Color(MultiplatformResource.colors.primaryColor.getColor(context)),
        override val secondary: Color = Color(MultiplatformResource.colors.secondaryColor.getColor(context)),
        override val thirdly: Color = Color(MultiplatformResource.colors.thirdlyColor.getColor(context)),
        override val white: Color = Color(MultiplatformResource.colors.white.getColor(context)),
        override val black: Color = Color(MultiplatformResource.colors.black.getColor(context)),
        override val error: Color = Color(MultiplatformResource.colors.errorColor.getColor(context)),
        override val border: Color = Color(MultiplatformResource.colors.borderColor.getColor(context)),
        override val red: Color = Color(MultiplatformResource.colors.red.getColor(context)),
        override val gray: Color = Color(MultiplatformResource.colors.gray.getColor(context)),
        override val transparent: Color = Color.Transparent,
    ): Colors()
}