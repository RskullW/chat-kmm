package com.chatkmm.screen.authorization.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.chatkmm.entity.PhoneNumberCountry
import com.chatkmm.resources.MultiplatformResource
import com.chatkmm.ui.B
import com.chatkmm.ui.MainTheme
import com.chatkmm.ui.button.ImageButton

@Composable
fun CountryDialog(
    countries: List<PhoneNumberCountry>,
    onSelectItem: (PhoneNumberCountry) -> Unit,
    cancelAction: () -> Unit,
) {
    Dialog(onDismissRequest = cancelAction,
        properties = DialogProperties( usePlatformDefaultWidth = false )
    ) {

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .shadow(12.dp, RoundedCornerShape(5.dp))
            .background(B.colors().white),
        ) {
            Row(modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 24.dp)
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {

                Text(
                    modifier = Modifier,
                    text = "Выбор региона",
                    style = B.typography().main.title,
                    color = B.colors().secondary
                )

                ImageButton(modifierIcon = Modifier, iconId = MultiplatformResource.images.ic_cross.drawableResId) {
                    cancelAction()
                }
            }

            HorizontalDivider(
                modifier = Modifier,
                thickness = 1.dp,
                color = B.colors().secondary
            )

            LazyColumn(
                horizontalAlignment = Alignment.Start
            ) {
                items(countries.size) { index ->
                    CountryItemDialog(modifier = Modifier, country = countries[index]) {
                        onSelectItem(countries[index])
                        cancelAction()
                    }
                }
            }
        }
    }
}


@Composable
@Preview
internal fun CountryDialog_Preview() {
    MainTheme {
        Box(
            modifier = Modifier
                .background(B.colors().white.copy(alpha = 0.3f))
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CountryDialog(
                countries = listOf(
                    PhoneNumberCountry(
                        country = "Russia",
                        region = "RU",
                        regionCode = "+7"
                    )
                ),
                onSelectItem = { /*TODO*/ },
                cancelAction = { /* TODO */})
        }
    }
}