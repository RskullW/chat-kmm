package com.chatkmm.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    hintText: String,
    onTextChange: (String) -> Unit = {},
    singleLine: Boolean = false,
    minLines: Int = 1,
    trailingIcon: @Composable (() -> Unit)? = null,
    height: Dp = 56.dp,
    textSelection: Int = 0,
    errorText: String? = null,
    isError: Boolean = false,
    isEnabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLength: Int = Int.MAX_VALUE,
    primaryColor: Color = B.colors().secondary,
) {
    val focusManager = LocalFocusManager.current

    var isFocused by remember { mutableStateOf(false) }

    val textColor = if (isError || !errorText.isNullOrEmpty()) {
        B.colors().error
    } else {
        primaryColor
    }

    val borderColor = if (isError || !errorText.isNullOrEmpty()) {
        B.colors().error
    } else if (isFocused) {
        primaryColor
    } else {
        primaryColor.copy(alpha = 0.6f)
    }

    Box(
        modifier = modifier
            .heightIn(min = height)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp)
            ),
    ) {

        val customTextSelectionColors = TextSelectionColors(
            handleColor = B.colors().secondary,
            backgroundColor = B.colors().transparent
        )

        var textFieldValue by remember {
            mutableStateOf(
                TextFieldValue(
                        text = text,
                        selection = TextRange(textSelection)
                    )
            )
        }

        LaunchedEffect(text) {
            textFieldValue = textFieldValue.copy(text = text, selection = TextRange(text.lastIndex + 1))
        }

        if (textFieldValue.text.isEmpty()) {
            Text(
                modifier = Modifier
                    .padding(start = 20.dp, top = 14.dp)
                    .fillMaxWidth(),
                text = hintText,
                style = if (isFocused) B.typography().main.hintText else B.typography().main.inputText,
                color = textColor
            )
        }
        else {
            Text(
                modifier = Modifier
                    .padding(start = 20.dp, top = 14.dp),
                text = hintText,
                style = B.typography().main.hintText,
                color = textColor
            )
        }

        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            BasicTextField(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(
                        top = if (!isFocused && textFieldValue.text.isEmpty()) 0.dp else 34.dp,
                        start = 20.dp,
                        bottom = if (!isFocused && textFieldValue.text.isEmpty()) 0.dp else 12.dp,
                        end = 8.dp
                    )
                    .fillMaxWidth()
                    .onFocusChanged {
                        if (!isEnabled) {
                            focusManager.clearFocus()
                        }
                        isFocused = it.isFocused
                    },
                enabled = isEnabled,
                value = textFieldValue,
                singleLine = singleLine,
                minLines = minLines,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                },
                    onDone = {
                        focusManager.moveFocus(FocusDirection.Down)
                        focusManager.clearFocus()
                    }),
                textStyle = B.typography().main.inputText,
                onValueChange = { newValue ->
                    if (isEnabled) {
                        if (newValue.text.length > maxLength) {
                            return@BasicTextField
                        }

                        textFieldValue = newValue
                        onTextChange(textFieldValue.text)
                    }
                },
                visualTransformation = if (keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None,
                )
        }

        trailingIcon?.let {
            Box(
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                trailingIcon()
            }
        }
    }
    if (errorText != null) {
        Text(
            modifier = Modifier.padding(
                top = 0.dp,
                start = 20.dp,
                end = 20.dp,
            ),
            text = errorText,
            overflow = TextOverflow.Ellipsis,
            color = B.colors().error,
            style = B.typography().main.inputText,
            textAlign = TextAlign.Start
        )
    }
}

@Preview(name = "Light Mode", backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
internal fun TextField_Preview() {
    MainTheme {
        Column(modifier = Modifier
            .padding(24.dp)) {
            InputTextField(
                text = "123",
                hintText = "Пароль",
                isEnabled = true,
                isError = true,
                singleLine = false,
                errorText = "23",
                )
        }
    }
}
