package com.chatkmm.features.screen.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chatkmm.ui.B
import com.chatkmm.ui.MainTheme

@Composable
fun ChatScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(B.colors().white),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Widmeyer Template",
            style = B.typography().text.title,
            color = B.colors().secondary
        )
    }
}

@Composable
@Preview
internal fun ChatScreenContent_Preview() {
    MainTheme {
        ChatScreenContent()
    }
}