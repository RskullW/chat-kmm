package com.chatkmm.screen.profile

import android.graphics.Bitmap
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.chatkmm.base.features.enum.Screen
import com.chatkmm.base.features.enum.Zodiac
import com.chatkmm.data.utils.globalApplicationContext
import com.chatkmm.data.utils.localize
import com.chatkmm.resources.MultiplatformResource
import com.chatkmm.ui.B
import com.chatkmm.ui.InputTextField
import com.chatkmm.ui.MainTheme
import com.chatkmm.ui.button.ImageButton
import com.chatkmm.ui.button.MainButton
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ProfileScreenContent(
    isEnabledButton: Boolean,
    name: String,
    userName: String,
    city: String,
    birthday: String,
    aboutMe: String,
    zodiac: Zodiac?,
    profileImageUrl: String,
    phoneNumber: String,
    onNameChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
    onBirthdayChange: (String) -> Unit,
    onAboutMeChange: (String) -> Unit,
    onSetImage: () -> Unit,
    onSave: () -> Unit,
    onExit: () -> Unit,
    onSetScreen: (Screen?) -> Unit,
) {
    var bitmapItem by remember { mutableStateOf<Bitmap?>(null) }
    val zodiacIconId: Int? = zodiac?.getImageFromZodiacId()

    val offsetY by animateDpAsState(
        targetValue = if (isEnabledButton) 0.dp else 100.dp,
        animationSpec = tween(durationMillis = 300)
    )

    LaunchedEffect(profileImageUrl) {
        if (profileImageUrl.isNotEmpty()) {
            val bitmap = withContext(Dispatchers.IO) {
                Picasso.get().load(profileImageUrl)
                    .placeholder(MultiplatformResource.images.ic_avatar.drawableResId)
                    .get()
            }

            bitmapItem = bitmap
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(B.colors().primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        // App Bar
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ImageButton(
                modifierIcon = Modifier,
                iconColor = B.colors().black,
                iconId = MultiplatformResource.images.ic_back.drawableResId
            ) {
                onSetScreen(null)
            }

            ImageButton(
                size = 32,
                modifierIcon = Modifier,
                iconColor = B.colors().black,
                iconId = MultiplatformResource.images.ic_exit.drawableResId
            ) {
                onExit()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(B.colors().white),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 24.dp)
                            .background(B.colors().blue.copy(alpha = 0.6f), CircleShape)
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (bitmapItem == null) {
                            Image(
                                modifier = Modifier
                                    .size(120.dp),
                                painter = painterResource(id = MultiplatformResource.images.ic_avatar.drawableResId),
                                contentDescription = "avatar"
                            )
                        } else {
                            Image(
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape),
                                bitmap = bitmapItem!!.asImageBitmap(),
                                contentDescription = "avatar",
                                contentScale = ContentScale.Crop
                            )
                        }

                        ImageButton(
                            size = 32,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(y = (-32).dp, x = 32.dp),
                            modifierIcon = Modifier
                                .padding(2.dp),
                            iconId = MultiplatformResource.images.ic_edit.drawableResId,
                            iconColor = B.colors().secondary,
                            onClick = onSetImage
                        )
                    }

                    Row(
                        modifier = Modifier
                            .padding(bottom = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 12.dp, end = 12.dp),
                            text = userName,
                            style = B.typography().profile.userName,
                            color = B.colors().secondary
                        )

                        if (zodiacIconId != null) {
                            Icon(
                                modifier = Modifier
                                    .size(24.dp),
                                painter = painterResource(id = zodiacIconId),
                                contentDescription = "zodiac",
                                tint = B.colors().secondary
                            )
                        }
                    }

                    InputTextField(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(B.colors().black.copy(alpha = 0.3f)),
                        hintText = MultiplatformResource.strings.phoneNumber.localize(),
                        isEnabled = false,
                        text = phoneNumber
                    )

                    InputTextField(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
                        hintText = MultiplatformResource.strings.name.localize(),
                        text = name,
                        onTextChange = onNameChange
                    )

                    InputTextField(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        hintText = MultiplatformResource.strings.city.localize(),
                        text = city,
                        onTextChange = onCityChange
                    )

                    InputTextField(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        hintText = MultiplatformResource.strings.birthday.localize(),
                        text = birthday,
                        onTextChange = onBirthdayChange,
                        keyboardType = KeyboardType.Number
                    )

                    InputTextField(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        hintText = MultiplatformResource.strings.aboutMe.localize(),
                        text = aboutMe,
                        onTextChange = onAboutMeChange,
                        singleLine = false,
                        minLines = 5,
                    )
                }
            }

            MainButton(
                modifier = Modifier
                    .offset(y = offsetY)
                    .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
                text = MultiplatformResource.strings.save.localize(),
                isEnabled = isEnabledButton,
                onClick = onSave,
                textColor = B.colors().secondary
            )
        }
    }
}

@Composable
@Preview
internal fun ProfileScreenContent_Preview() {
    globalApplicationContext = LocalContext.current

    var isEnabled by remember { mutableStateOf(false) }
    MainTheme {
        ProfileScreenContent(
            isEnabledButton = isEnabled,
            name = "Alexander", 
            userName = "RskullW-Widmeyer",
            profileImageUrl = "",
            phoneNumber = "+7 911 111 11 11",
            city = "Санкт-Петербург",
            birthday = "21.11.2024",
            aboutMe = "",
            zodiac = Zodiac.PISCES,
            onExit = {

            },
            onSetImage = {

            },
            onNameChange = {
                isEnabled = true
            },
            onBirthdayChange = {
                isEnabled = true
            },
            onCityChange = {
                isEnabled = true
            },
            onAboutMeChange = {
                isEnabled = true
            },
            onSave = {
                isEnabled = false
            },
            onSetScreen = {

            },
        )
    }
}

internal fun Zodiac.getImageFromZodiacId(): Int? {
    val images = MultiplatformResource.images
    val imageResource = when (this) {
        Zodiac.AQUARIUS -> images.aquarius
        Zodiac.ARIES -> images.aries
        Zodiac.CANCER -> images.cancer
        Zodiac.CAPRICORN -> images.capricorn
        Zodiac.GEMINI -> images.gemini
        Zodiac.LEO -> images.leo
        Zodiac.LIBRA -> images.libra
        Zodiac.PISCES -> images.pisces
        Zodiac.SAGITTARIUS -> images.sagittarius
        Zodiac.SCORPIO -> images.scorpio
        Zodiac.TAURUS -> images.taurus
        Zodiac.VIRGO -> images.virgo
        else -> null
    }

    return imageResource?.drawableResId
}