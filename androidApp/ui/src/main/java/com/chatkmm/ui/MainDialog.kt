package com.chatkmm.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.chatkmm.ui.button.MainButton

@Composable
fun MainDialog(
    title: String,
    description: String,
    confirmText: String? = null,
    cancelText: String,
    confirmAction: () -> Unit = {},
    cancelAction: () -> Unit,
) {
    Dialog(onDismissRequest = cancelAction,
        properties = DialogProperties( usePlatformDefaultWidth = false )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .shadow(12.dp, RoundedCornerShape(12.dp))
                .background(B.colors().white)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(bottom = 16.dp),
                text = title,
                style = B.typography().dialog.title,
                color = B.colors().thirdly
            )

            Text(
                modifier = Modifier
                    .padding(bottom = 32.dp),
                text = description,
                style = B.typography().dialog.description,
                color = B.colors().thirdly
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (!confirmText.isNullOrEmpty()) {
                    MainButton(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .weight(1.0f),
                        text = confirmText,
                        onClick = confirmAction,
                        backgroundColor = B.colors().primary,
                        textColor = B.colors().secondary,
                        textStyle = B.typography().dialog.buttonText
                    )
                }

                MainButton(
                    modifier = Modifier
                        .weight(1.0f),
                    text = cancelText,
                    backgroundColor = B.colors().secondary,
                    onClick = cancelAction,
                    textStyle = B.typography().dialog.buttonText
                )
            }
        }
    }
}

@Composable
@Preview
internal fun MainDialogConfirm_Preview() {
    MainTheme {
        Box(
            modifier = Modifier
                .background(B.colors().white.copy(alpha = 0.3f))
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            MainDialog(
                title = "Title",
                description = "Description",
                confirmText = "Confirm",
                cancelText = "Cancel",
                confirmAction = { /*TODO*/ },
                cancelAction = { /* TODO */})
        }
    }
}

@Composable
@Preview
internal fun MainDialogCancel_Preview() {
    MainTheme {
        Box(
            modifier = Modifier
                .background(B.colors().white.copy(alpha = 0.3f))
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            MainDialog(
                title = "Title",
                description = "Description",
                confirmText = "",
                cancelText = "Cancel",
                confirmAction = { /*TODO*/ },
                cancelAction = { /* TODO */})
        }
    }
}