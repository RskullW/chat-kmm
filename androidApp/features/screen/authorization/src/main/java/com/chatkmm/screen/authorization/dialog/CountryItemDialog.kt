package com.chatkmm.screen.authorization.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chatkmm.entity.PhoneNumberCountry
import com.chatkmm.ui.B
import com.chatkmm.ui.MainTheme

@Composable
fun CountryItemDialog(
    modifier: Modifier,
    country: PhoneNumberCountry,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .clickable { 
                       onClick()
            },
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier
            .background(B.colors().white)
            .padding(horizontal = 16.dp, vertical = 18.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
            ) {

            Text(
                modifier = Modifier
                    .padding(end = 16.dp),
                text = country.regionCode,
                style = B.typography().dialog.title,
                color = B.colors().secondary
            )

            VerticalDivider(
                modifier = Modifier.padding(end = 16.dp)
            )

            Text(
                modifier = Modifier
                    .padding(end = 16.dp),
                text = country.country,
                style = B.typography().dialog.title,
                color = B.colors().secondary
            )

        }

        HorizontalDivider(
            modifier = Modifier,
            thickness = 1.dp,
            color = B.colors().secondary
        )
    }
}

@Preview
@Composable
internal fun CountryItemDialog_Preview() {
    MainTheme {
        CountryItemDialog(
            modifier = Modifier,
            country = PhoneNumberCountry(
                country = "Russia",
                region = "RU",
                regionCode = "+7"
            ),
            onClick = {})
    }
}