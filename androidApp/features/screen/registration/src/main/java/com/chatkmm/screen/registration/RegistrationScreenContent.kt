package com.chatkmm.screen.registration

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.chatkmm.data.utils.globalApplicationContext
import com.chatkmm.data.utils.localize
import com.chatkmm.resources.MultiplatformResource
import com.chatkmm.ui.B
import com.chatkmm.ui.InputTextField
import com.chatkmm.ui.MainTheme
import com.chatkmm.ui.button.MainButton

@Composable
fun RegistrationScreenContent(
    phoneNumber: String,
    name: String,
    username: String,
    errorText: String?,
    onSetName: (String) -> Unit,
    onSetUsername: (String) -> Unit,
    onRegister: () -> Unit,
) {
    var isTextVisible by remember { mutableStateOf(false) }
    var isCircleVisible by remember { mutableStateOf(false) }
    var isAllElementsVisible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        isTextVisible = true
    }

    val offsetXWelcome by animateDpAsState(
        targetValue = if (isTextVisible) 0.dp else (-1000).dp,
        animationSpec = tween(durationMillis = 2000),
        finishedListener = { isCircleVisible = true }
    )

    val circleRadius by animateDpAsState(
        targetValue = if (isCircleVisible) 2000.dp else 0.dp,
        animationSpec = tween(durationMillis = 1700),
        finishedListener = { isAllElementsVisible = true }
    )

    val offsetXElements by animateDpAsState(
        targetValue = if (isAllElementsVisible) 0.dp else (1000).dp,
        animationSpec = tween(durationMillis = 1200),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(B.colors().primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .offset(x = offsetXWelcome),
            text = MultiplatformResource.strings.welcome.localize(),
            style = B.typography().registration.title,
            fontStyle = FontStyle.Italic
        )
        Box(modifier = Modifier
            .fillMaxSize(),
            contentAlignment = Alignment.BottomStart
        ) {
            if (!isAllElementsVisible) {
                Canvas(modifier = Modifier) {
                    val centerX = size.width / 2
                    val centerY = size.height / 2

                    drawCircle(
                        color = Color.White,
                        radius = circleRadius.toPx(),
                        center = Offset(centerX, centerY)
                    )
                }
            } else {
                Column(modifier = Modifier
                    .background(B.colors().white)
                    .padding(vertical = 32.dp)
                    .offset(y = offsetXElements)
                    .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .padding(bottom = 16.dp),
                        text = MultiplatformResource.strings.registration.localize(),
                        style = B.typography().registration.title
                    )

                    InputTextField(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(B.colors().gray),
                        hintText = MultiplatformResource.strings.phoneNumber.localize(),
                        isEnabled = false,
                        text = phoneNumber,
                        primaryColor = B.colors().black.copy(alpha = 0.4f),)

                    InputTextField(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
                        hintText = MultiplatformResource.strings.inputName.localize(),
                        text = name,
                        isError = !errorText.isNullOrEmpty(),
                        onTextChange = onSetName,
                    )

                    Column(modifier = Modifier,
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center) {
                        InputTextField(
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp),
                            hintText = MultiplatformResource.strings.inputSecondName.localize(),
                            text = username,
                            onTextChange = onSetUsername,
                            isError = !errorText.isNullOrEmpty(),
                            errorText = errorText,
                        )
                    }

                    Spacer(modifier = Modifier.weight(1.0f))

                    MainButton(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        text = MultiplatformResource.strings.join.localize(),
                        textColor = B.colors().secondary,
                        onClick = onRegister
                    )
                }
            }
        }
    }
}


@Composable
@Preview
internal fun RegistrationScreenContent_Preview() {
    globalApplicationContext = LocalContext.current
    MainTheme {
        RegistrationScreenContent(
            phoneNumber = "+7 911 111 11 11",
            name = "Ivan",
            username = "Ivanov",
            errorText = "123",
            onSetName = {

            },
            onSetUsername = {

            },
            onRegister = {

            }
        )
    }
}