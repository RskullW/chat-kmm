package com.chatkmm.screen.authorization

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.chatkmm.base.features.enum.Screen
import com.chatkmm.data.utils.format
import com.chatkmm.data.utils.globalApplicationContext
import com.chatkmm.data.utils.localize
import com.chatkmm.domain.constants.Constants
import com.chatkmm.entity.PhoneNumberCountry
import com.chatkmm.resources.MultiplatformResource
import com.chatkmm.ui.B
import com.chatkmm.ui.InputTextField
import com.chatkmm.ui.MainTheme
import com.chatkmm.ui.button.ImageButton
import com.chatkmm.ui.button.MainButton
import com.chatkmm.ui.indicator.PageIndicator

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AuthorizationScreenContent(
    pagerState: PagerState,
    currentPage: Int,
    numberOfPages: Int,
    currentPhoneNumberCountry: PhoneNumberCountry,
    phoneNumber: String,
    code: String,
    onSetPhoneNumber: (String) -> Unit,
    onSetCode: (String) -> Unit,
    onClickSelector: () -> Unit,
    onUpdatePage: (Int) -> Unit,
    onSetScreen: (Screen?) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(currentPage) {
        pagerState.animateScrollToPage(currentPage-1)
    }

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .background(B.colors().primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.weight(1.0f))

        HorizontalPager(
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth(),
            state = pagerState,
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> {
                    Column(modifier = Modifier
                        .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 32.dp, end = 32.dp, bottom = 16.dp),
                            text = MultiplatformResource.strings.authorizationDescription.localize(),
                            style = B.typography().main.title,
                            color = B.colors().secondary,
                            textAlign = TextAlign.Center,
                        )

                        Column(
                            modifier = Modifier
                                .padding(start = 32.dp, end = 32.dp, bottom = 16.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    onClickSelector()
                                }
                                .background(B.colors().white)
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Выбор региона",
                                style = B.typography().main.hintText,
                                color = B.colors().secondary
                            )

                            Text(
                                text = currentPhoneNumberCountry.country,
                                style = B.typography().main.main,
                                color = B.colors().secondary
                            )
                        }

                        InputTextField(
                            modifier = Modifier
                                .padding(horizontal = 32.dp),
                            hintText = MultiplatformResource.strings.phoneNumber.localize(),
                            text = phoneNumber,
                            onTextChange = onSetPhoneNumber,
                            keyboardType = KeyboardType.Phone)
                    }
                }
                1 -> {
                    Column(modifier = Modifier
                        .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 32.dp, end = 32.dp, bottom = 16.dp),
                            text = MultiplatformResource.strings.inputCode.localize(),
                            style = B.typography().main.title,
                            color = B.colors().thirdly,
                            textAlign = TextAlign.Center,
                        )

                        InputTextField(
                            modifier = Modifier
                                .padding(horizontal = 32.dp),
                            hintText = MultiplatformResource.strings.code.format(Constants.Numbers.codeLength.toString()),
                            text = code,
                            onTextChange = onSetCode,
                            keyboardType = KeyboardType.Number
                        )
                    }
                }
            }
        }

        MainButton(
            modifier = Modifier
                .padding(horizontal = 32.dp),
            text = MultiplatformResource.strings.resume.localize(),
            textColor = B.colors().white,
            backgroundColor = B.colors().secondary
        ) {
            onUpdatePage(currentPage+1)
        }

        if (currentPage > 1) {
            MainButton(
                modifier = Modifier
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                text = "Назад",
                textColor = B.colors().white,
                backgroundColor = B.colors().black.copy(0.3f)
            ) {
                onUpdatePage(currentPage-1)
            }

        }

        Spacer(modifier = Modifier.weight(1.0f))

        PageIndicator(
            modifier = Modifier
                .padding(bottom = 32.dp),
            currentPage = currentPage,
            numberOfPages = numberOfPages,
            defaultRadius = 15.dp,
            selectedLength = 30.dp,
            space = 15.dp,
            defaultColor = B.colors().black.copy(alpha = 0.2f),
            selectedColor = B.colors().secondary
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
internal fun AuthorizationScreenContent_Preview() {
    globalApplicationContext = LocalContext.current
    var currentPage by remember { mutableIntStateOf(2) }

    var phoneNumberCountry by remember {
        mutableStateOf(
            PhoneNumberCountry(
                country = "Russia",
                region = "RU",
                regionCode = "+7"
            ),
        )
    }

    MainTheme {
        AuthorizationScreenContent(
            currentPage = currentPage,
            pagerState = rememberPagerState(
                initialPage = currentPage,
                pageCount = { 2 }
            ),
            numberOfPages = 2,
            onUpdatePage = { newValue ->
                currentPage = newValue
            },
            currentPhoneNumberCountry = phoneNumberCountry,
            onClickSelector = {

            },
            phoneNumber = "",
            code = "",
            onSetPhoneNumber = {

            },
            onSetCode = {

            },
            onSetScreen = { _ ->

            }
        )
    }
}