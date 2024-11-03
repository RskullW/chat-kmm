package com.chatkmm.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chatkmm.resources.MultiplatformResource

@Composable
fun FavoriteBox(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    backgroundColor: Color = B.colors().white,
    onSetFavorite: () -> Unit,
) {
    Box(modifier = modifier,
            contentAlignment = Alignment.Center
    ) {
        Box(modifier = modifier
            .shadow(elevation = 20.dp)
            .clip(RoundedCornerShape(1000.dp))
            .clickable {
                onSetFavorite()
            }
            .background(backgroundColor)
            .padding(8.dp)
            ,
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(18.dp),
                painter = painterResource(MultiplatformResource.images.placeholder.drawableResId), // TODO: IMR_000 to 'ic_heart.svg'
                contentDescription = null,
                tint = if (isFavorite) B.colors().red else B.colors().black.copy(alpha = 0.2f)
            )
        }
    }
}

@Preview
@Composable
internal fun FavoriteBox_Preview() {
    var favorite by remember { mutableStateOf(false) }

    MainTheme {
        Row(modifier = Modifier
            .background(B.colors().primary)
            .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
            ) {
            FavoriteBox(
                isFavorite = favorite
            ) {
                favorite = !favorite
            }
        }
    }
}