package com.chatkmm.ui


import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp

@Immutable
data class Typography(private val mainTextFont: FontFamily) {
    val text: TitleTypography = TitleTypography(mainTextFont)
}

@Immutable
data class TitleTypography(
    private val mainTextFont: FontFamily,
) {
    val title: TextStyle = TextStyle(
        fontSize = 22.sp,
        lineHeight = 28.sp,
        fontFamily = mainTextFont,
        fontWeight = FontWeight.W600
    ).preciseLineHeight()

    val main: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 28.sp,
        fontFamily = mainTextFont,
        fontWeight = FontWeight.W400
    ).preciseLineHeight()

    val buttonText: TextStyle = TextStyle(
        fontSize = 18.sp,
        lineHeight = 22.sp,
        fontFamily = mainTextFont,
        fontWeight = FontWeight.W600,
    )

    val inputText: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 28.sp,
        fontFamily = mainTextFont,
        fontWeight = FontWeight.W400
    ).preciseLineHeight()

    val hintText: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 28.sp,
        fontFamily = mainTextFont,
        fontWeight = FontWeight.W400
    ).preciseLineHeight()
}

fun TextStyle.preciseLineHeight(): TextStyle = this.copy(
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    ),
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None
    )
)