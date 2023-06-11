package com.goforer.phogal.presentation.ui.compose.screen.home.gallery.common

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.goforer.base.designsystem.component.IconButton
import com.goforer.base.designsystem.component.IconContainer
import com.goforer.base.designsystem.component.ImageCrossFade
import com.goforer.base.designsystem.animation.GenericCubicAnimationShape
import com.goforer.base.designsystem.animation.animateIconScale
import com.goforer.base.designsystem.component.loadImagePainter
import com.goforer.base.extension.isNull
import com.goforer.phogal.R
import com.goforer.phogal.data.model.remote.response.gallery.common.User
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.common.UserContainerState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.common.UserInfoState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.common.rememberUserContainerState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.common.rememberUserInfoState
import com.goforer.phogal.presentation.ui.Caller
import com.goforer.phogal.presentation.ui.theme.Blue80
import com.goforer.phogal.presentation.ui.theme.DarkGreen60
import com.goforer.phogal.presentation.ui.theme.DarkGreenGray10
import com.goforer.phogal.presentation.ui.theme.DarkGreenGray99
import com.goforer.phogal.presentation.ui.theme.PhogalTheme
import com.goforer.phogal.presentation.ui.theme.Teal60
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserContainer(
    modifier: Modifier = Modifier,
    user: User,
    state: UserContainerState = rememberUserContainerState(),
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onShowSnackBar: (text: String) -> Unit
) {
    val lastName = user.last_name ?: stringResource(id = R.string.picture_no_last_name)
    var showUserInfoBottomSheet by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier.background(state.colors[2]),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp)
                .background(Color.Transparent)
                .wrapContentHeight(Alignment.CenterVertically)
                .fillMaxWidth()
                .heightIn(68.dp, 114.dp)
                .clickable {
                    showUserInfoBottomSheet = true
                },
        ) {
            IconContainer(state.profileSize.value.dp) {
                Box {
                    val painter = loadImagePainter(
                        data = user.profile_image.small,
                        size = Size.ORIGINAL
                    )

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
                                if (state.visibleViewPhotosButton.value)
                                    onViewPhotos(
                                        user.username,
                                        user.first_name,
                                        lastName,
                                        user.username
                                    )
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
            Column(modifier = Modifier
                .height(IntrinsicSize.Min)
                .widthIn(186.dp)
            ) {
                Text(
                    text = user.name,
                    color = state.colors[0],
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Normal,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "${user.total_likes}${" "}" +
                            "${stringResource(id = R.string.picture_likes)}${" "}${user.total_collections}${" "}" +
                            "${stringResource(id = R.string.picture_collections)}${" "}" +
                            "${stringResource(id = R.string.user_updated_at)}${" "}${user.updated_at}",
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Normal,
                    color = state.colors[1],
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }

        if (state.visibleViewPhotosButton.value) {
            IconButton(
                modifier = Modifier.padding(start = 56.dp, top = 0.dp, bottom = 2.dp),
                height = 32.dp,
                colors = ButtonDefaults.buttonColors(
                    containerColor = state.colors[3],
                    contentColor = state.colors[4]
                ),
                onClick = { onViewPhotos(user.username, user.first_name,  lastName, user.username) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Photo,
                        contentDescription = null,
                    )
                },
                text = {
                    Text(
                        "${stringResource(id = R.string.picture_view_photos, user.name)}${" "}${user.total_photos}${" "}${stringResource(id = R.string.picture_photos, user.name)}",
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
            )
        }
    }

    if (showUserInfoBottomSheet) {
        UserInfoBottomSheet(
            userInfoState = rememberUserInfoState(),
            user = user,
            onDismissedRequest = {
                showUserInfoBottomSheet = false
            },
            onShowSnackBar = onShowSnackBar
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoBottomSheet(
    userInfoState: UserInfoState = rememberUserInfoState(),
    user: User,
    onDismissedRequest: () -> Unit,
    onShowSnackBar: (text: String) -> Unit
) {
    GenericCubicAnimationShape(400) { animatedShape ->
        ModalBottomSheet(
            onDismissRequest = {
                userInfoState.scope.launch {
                    userInfoState.bottomSheetState.hide()
                }.invokeOnCompletion {
                    if (!userInfoState.bottomSheetState.isVisible) {
                        userInfoState.openBottomSheetState.value = false
                    }
                }

                onDismissedRequest()
            },
            sheetState = userInfoState.bottomSheetState,
            shape = animatedShape,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.wrapContentHeight(),
                horizontalAlignment = Alignment.Start,
            ) {
                ProfileItem(
                    image = user.profile_image.medium,
                    name = user.name,
                    position = 9
                )
                Spacer(modifier = Modifier.height(8.dp))
                UserInfoItem(
                    text = user.bio ?: stringResource(id = R.string.user_info_no_sex_info),
                    painter = painterResource(id = R.drawable.ic_bio),
                    position = 8
                )
                Spacer(modifier = Modifier.height(8.dp))
                UserInfoItem(
                    text = user.location ?: stringResource(id = R.string.user_info_no_location_info),
                    painter = painterResource(id = R.drawable.ic_location),
                    position = 7
                )
                Spacer(modifier = Modifier.height(8.dp))
                UserInfoItem(
                    text = "${stringResource(id = R.string.user_info_instagram_name)}${" "}${user.instagram_username ?: stringResource(id = R.string.user_info_no_instagram_name)}",
                    painter = painterResource(id = R.drawable.ic_instagram),
                    position = 6
                )
                Spacer(modifier = Modifier.height(8.dp))
                UserInfoItem(
                    text = "${stringResource(id = R.string.user_info_twitter_name)}${" "}${user.twitter_username ?: stringResource(id = R.string.user_info_no_twitter_name)}",
                    painter = painterResource(id = R.drawable.ic_twitter),
                    position = 5
                )
                Spacer(modifier = Modifier.height(8.dp))
                UserInfoItem(
                    text = user.links.followers ?: "",
                    painter = painterResource(id = R.drawable.ic_follower),
                    position = 4
                )
                Spacer(modifier = Modifier.height(8.dp))
                UserInfoItem(
                    text = user.links.following ?: "",
                    painter = painterResource(id = R.drawable.ic_following),
                    position = 3
                )
                Spacer(modifier = Modifier.height(8.dp))
                UserInfoItem(
                    text = "${stringResource(id = R.string.user_updated_at)}${" "}${user.updated_at}",
                    painter = painterResource(id = R.drawable.ic_date),
                    position = 2
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val animationIconScale = animateIconScale(inputScale = 0.6F, position = 1, delay = 150L)

                    Image(
                        painter = painterResource(id = R.drawable.ic_portfolio),
                        contentDescription = "Following",
                        modifier = Modifier
                            .size(22.dp)
                            .padding(horizontal = 4.dp)
                            .graphicsLayer {
                                scaleX = animationIconScale
                                scaleY = animationIconScale
                            }
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    IconButton(
                        modifier = Modifier.padding(horizontal = 2.dp),
                        height = 32.dp,
                        onClick = {},
                        icon = {
                            Icon(
                                imageVector = Icons.Default.OpenInBrowser,
                                contentDescription = null,
                            )
                        },
                        text = {
                            val phrase = stringResource(id = R.string.user_info_has_no_portfolio)

                            Text(
                                text = stringResource(id = R.string.user_info_portfolio, user.first_name),
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .clickable {
                                        user.portfolio_url.isNull({
                                            userInfoState.baseUiState.scope.launch {
                                                onShowSnackBar("${user.first_name}${" "}${phrase}")
                                            }
                                        }, { url ->
                                            userInfoState.baseUiState.context?.let { context ->
                                                Caller.openBrowser(
                                                    context, url
                                                )
                                            }
                                        })
                                    },
                                color = DarkGreenGray99,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                fontStyle = FontStyle.Normal,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(36.dp))
            }
        }
    }
}

@Composable
fun ProfileItem(image: String, name: String, position: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .background(Color.Transparent)
            .wrapContentHeight(Alignment.CenterVertically)
            .fillMaxWidth()
            .heightIn(68.dp, 114.dp)
            .clickable {

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
            color = DarkGreenGray10,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            fontStyle = FontStyle.Normal,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun UserInfoItem(text: String, painter: Painter, position: Int) {
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
            color = DarkGreenGray10,
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
fun UserContainerPreview() {
    PhogalTheme {
        Column(
            modifier = Modifier.background(DarkGreen60),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp)
                    .background(Color.Transparent)
                    .wrapContentHeight(Alignment.CenterVertically)
                    .fillMaxWidth()
                    .heightIn(68.dp, 114.dp)
                    .clickable {},
            ) {
                IconContainer(36.dp) {
                    Box {
                        val painter = loadImagePainter(
                            data = "https://avatars.githubusercontent.com/u/18302717?v=4",
                            size = Size.ORIGINAL
                        )

                        ImageCrossFade(painter = painter, durationMillis = null)
                        Image(
                            painter = painter,
                            contentDescription = "ComposeTest",
                            modifier = Modifier
                                .padding(1.dp)
                                .fillMaxSize()
                                .clip(CircleShape)
                                .border(0.5.dp, MaterialTheme.colorScheme.secondary, CircleShape),
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
                Column(modifier = Modifier
                    .wrapContentHeight()
                    .widthIn(178.dp)
                ) {
                    Text(
                        "Lukoh",
                        color = Color.White,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Normal,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "https://api.unsplash.com/users/jimmyexample/portfolio",
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 13.sp,
                        fontStyle = FontStyle.Normal,
                        color = Color.White,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }

            IconButton(
                modifier = Modifier.padding(start = 56.dp, top = 0.dp, bottom = 2.dp),
                height = 32.dp,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue80,
                    contentColor = Teal60
                ),
                onClick = {},
                icon = {
                    Icon(
                        imageVector = Icons.Default.Photo,
                        contentDescription = null,
                    )
                },
                text = {
                    Text(
                        stringResource(id = R.string.picture_view_photos, "Lukoh"),
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
            )
        }
    }
}