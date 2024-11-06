package com.chatkmm.ui


import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp

@Immutable
data class Typography(private val regular: FontFamily, private val bold: FontFamily) {
    val main: TitleTypography = TitleTypography(regular, bold)
    val registration: RegistrationTypography = RegistrationTypography(regular, bold)
    val dialog: DialogTypography = DialogTypography(regular, bold)
    val chat: ChatTypography = ChatTypography(regular, bold)
    val profile: ProfileTypography = ProfileTypography(regular, bold)
}

@Immutable
data class TitleTypography(
    private val regular: FontFamily,
    private val bold: FontFamily,
) {
    val splash: TextStyle = TextStyle(
        fontSize = 32.sp,
        lineHeight = 28.sp,
        fontFamily = bold,
        fontWeight = FontWeight.W400
    ).preciseLineHeight()

    val title: TextStyle = TextStyle(
        fontSize = 22.sp,
        lineHeight = 28.sp,
        fontFamily = bold,
        fontWeight = FontWeight.W400
    ).preciseLineHeight()

    val main: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 28.sp,
        fontFamily = regular,
        fontWeight = FontWeight.W400
    ).preciseLineHeight()

    val buttonText: TextStyle = TextStyle(
        fontSize = 18.sp,
        lineHeight = 22.sp,
        fontFamily = bold,
        fontWeight = FontWeight.W400,
    )

    val inputText: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 28.sp,
        fontFamily = regular,
        fontWeight = FontWeight.W400
    ).preciseLineHeight()

    val hintText: TextStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 12.sp,
        fontFamily = regular,
        fontWeight = FontWeight.W400
    ).preciseLineHeight()
}

@Immutable
data class DialogTypography(
    private val regular: FontFamily,
    private val bold: FontFamily,
) {
    val title: TextStyle = TextStyle(
        fontSize = 20.sp,
        lineHeight = 28.sp,
        fontFamily = bold,
        fontWeight = FontWeight.W400
    ).preciseLineHeight()

    val description: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 28.sp,
        fontFamily = regular,
        fontWeight = FontWeight.W400
    ).preciseLineHeight()

    val buttonText: TextStyle = TextStyle(
        fontSize = 18.sp,
        lineHeight = 22.sp,
        fontFamily = bold,
        fontWeight = FontWeight.W400,
    )
}

@Immutable
data class RegistrationTypography(
    private val regular: FontFamily,
    private val bold: FontFamily,
) {
    val title: TextStyle = TextStyle(
        fontSize = 28.sp,
        lineHeight = 28.sp,
        fontFamily = regular,
        fontWeight = FontWeight.W600
    ).preciseLineHeight()

    val inputText: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 28.sp,
        fontFamily = regular,
        fontWeight = FontWeight.W400
    ).preciseLineHeight()

    val text: TextStyle = TextStyle(
        fontSize = 20.sp,
        lineHeight = 28.sp,
        fontFamily = regular,
        fontWeight = FontWeight.W400
    ).preciseLineHeight()

    val buttonText: TextStyle = TextStyle(
        fontSize = 18.sp,
        lineHeight = 22.sp,
        fontFamily = regular,
        fontWeight = FontWeight.W600,
    )
}

@Immutable
data class ChatTypography(
    private val regular: FontFamily,
    private val bold: FontFamily,
) {
    val name: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 10.sp,
        fontFamily = bold,
        fontWeight = FontWeight.W400
    ).preciseLineHeight()

    val message: TextStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 10.sp,
        fontFamily = regular,
        fontWeight = FontWeight.W400
    ).preciseLineHeight()

    val date: TextStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 10.sp,
        fontFamily = bold,
        fontWeight = FontWeight.W400,
    )
}

@Immutable
data class ProfileTypography(
    private val regular: FontFamily,
    private val bold: FontFamily,
) {
    val userName: TextStyle = TextStyle(
        fontSize = 24.sp,
        lineHeight = 10.sp,
        fontFamily = bold,
        fontWeight = FontWeight.W600
    ).preciseLineHeight()

    val inputText: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 28.sp,
        fontFamily = regular,
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