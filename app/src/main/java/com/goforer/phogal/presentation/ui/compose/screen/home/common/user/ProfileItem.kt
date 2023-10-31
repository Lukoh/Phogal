package com.goforer.phogal.presentation.ui.compose.screen.home.common.user

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.size.Size
import com.goforer.base.designsystem.animation.animateIconScale
import com.goforer.base.designsystem.component.IconContainer
import com.goforer.base.designsystem.component.ImageCrossFade
import com.goforer.base.designsystem.component.loadImagePainter
import com.goforer.phogal.R
import com.goforer.phogal.data.model.local.home.common.ProfileInfoItem
import com.goforer.phogal.data.model.remote.response.gallery.common.UserUiState
import com.goforer.phogal.presentation.ui.theme.PhogalTheme

@Composable
fun ProfileItem(
    image: String,
    name: String,
    nameColor: Color,
    position: Int,
    onClicked: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .background(Color.Transparent)
            .wrapContentHeight(Alignment.CenterVertically)
            .heightIn(68.dp, 114.dp)
            .clickable {
                onClicked()
            },
    ) {
        IconContainer(64.dp) {
            Box {
                val painter = loadImagePainter(
                    data = image,
                    size = Size.ORIGINAL
                )
                val animationIconScale = animateIconScale(inputScale = 0.6F, position = position, delay = 150L)

                ImageCrossFade(painter = painter, durationMillis = null)
                Image(
                    painter = painter,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .padding(1.dp)
                        .fillMaxSize()
                        .clip(CircleShape)
                        .border(0.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
                        .clickable {
                            onClicked()
                        }
                        .graphicsLayer {
                            scaleX = animationIconScale
                            scaleY = animationIconScale
                        },
                    Alignment.CenterStart,
                    contentScale = ContentScale.Crop
                )

                if (painter.state is AsyncImagePainter.State.Loading) {
                    val preloadPainter = loadImagePainter(
                        data = R.drawable.ic_profile_logo,
                        size = Size.ORIGINAL
                    )

                    Image(
                        painter = preloadPainter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(36.dp)
                            .align(Alignment.Center),
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = name,
            color = nameColor,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            fontStyle = FontStyle.Normal,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun getProfileInfoItems(userUiState: UserUiState) = listOf(
    ProfileInfoItem(
        text = userUiState.bio ?: stringResource(id = R.string.user_info_no_sex_info),
        painter = painterResource(id = R.drawable.ic_bio),
        position = 8
    ),
    ProfileInfoItem(
        text = userUiState.location ?: stringResource(id = R.string.user_info_no_location_info),
        painter = painterResource(id = R.drawable.ic_location),
        position = 7
    ),
    ProfileInfoItem(
        text = "${stringResource(id = R.string.user_info_instagram_name)}${" "}${userUiState.instagram_username ?: stringResource(id = R.string.user_info_no_instagram_name)}",
        painter = painterResource(id = R.drawable.ic_instagram),
        position = 6
    ),
    ProfileInfoItem(
        text = "${stringResource(id = R.string.user_info_twitter_name)}${" "}${userUiState.twitter_username ?: stringResource(id = R.string.user_info_no_twitter_name)}",
        painter = painterResource(id = R.drawable.ic_twitter),
        position = 5
    ),
    ProfileInfoItem(
        text = userUiState.links.followers ?: "",
        painter = painterResource(id = R.drawable.ic_follower),
        position = 4
    ),
    ProfileInfoItem(
        text = userUiState.links.following ?: "",
        painter = painterResource(id = R.drawable.ic_following),
        position = 3
    ),
    ProfileInfoItem(
        text = "${stringResource(id = R.string.user_updated_at)}${" "}${userUiState.updated_at}",
        painter = painterResource(id = R.drawable.ic_date),
        position = 2
    )
)

@Composable
fun UserInfoItem(text: String, textColor: Color, painter: Painter, position: Int) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val animationIconScale = animateIconScale(inputScale = 0.6F, position = position, delay = 150L)

        Image(
            painter = painter,
            contentDescription = "UserInfoItem",
            modifier = Modifier
                .size(22.dp)
                .padding(horizontal = 4.dp)
                .graphicsLayer {
                    scaleX = animationIconScale
                    scaleY = animationIconScale
                }
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = textColor,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            fontStyle = FontStyle.Normal,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode",
    showSystemUi = true
)
@Composable
fun ProfileItemPreview(modifier: Modifier = Modifier) {
    PhogalTheme {
        val image = "https://avatars.githubusercontent.com/u/18302717?v=4"
        val name = "Lukoh"
        val position = 0

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .background(Color.Transparent)
                .wrapContentHeight(Alignment.CenterVertically)
                .heightIn(68.dp, 114.dp)
                .clickable {},
        ) {
            IconContainer(64.dp) {
                Box {
                    val painter = loadImagePainter(
                        data = image,
                        size = Size.ORIGINAL
                    )
                    val animationIconScale = animateIconScale(inputScale = 0.6F, position = position, delay = 150L)

                    ImageCrossFade(painter = painter, durationMillis = null)
                    Image(
                        painter = painter,
                        contentDescription = "Profile",
                        modifier = Modifier
                            .padding(1.dp)
                            .fillMaxSize()
                            .clip(CircleShape)
                            .border(0.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
                            .clickable {}
                            .graphicsLayer {
                                scaleX = animationIconScale
                                scaleY = animationIconScale
                            },
                        Alignment.CenterStart,
                        contentScale = ContentScale.Crop
                    )

                    if (painter.state is AsyncImagePainter.State.Loading) {
                        val preloadPainter = loadImagePainter(
                            data = R.drawable.ic_profile_logo,
                            size = Size.ORIGINAL
                        )

                        Image(
                            painter = preloadPainter,
                            contentDescription = null,
                            modifier = Modifier
                                .size(36.dp)
                                .align(Alignment.Center),
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = name,
                color = Color.White,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                fontStyle = FontStyle.Normal,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}