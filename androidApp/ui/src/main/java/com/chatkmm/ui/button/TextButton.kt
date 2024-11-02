package com.chatkmm.ui.button

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chatkmm.ui.B

@Composable
fun TextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    normalTextColor: Color = B.colors().primary,
    textStyle: TextStyle = B.typography().text.buttonText,
) {
    val isPressed = remember { mutableStateOf(false) }

    BasicText(
        text = text,
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed.value = true
                        tryAwaitRelease()
                        isPressed.value = false
                        onClick()
                    }
                )
            },
        style = textStyle.copy(
            color = if (isPressed.value) normalTextColor.copy(alpha = 0.7f) else normalTextColor
        )
    )
}


@Preview
@Composable
fun TextButton_Preview() {
    Column() {
        TextButton(
            text = "Click Me",
            modifier = Modifier
                .padding(bottom = 6.dp),
            onClick = { },
        )
    }
}