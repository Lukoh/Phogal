package com.goforer.phogal.presentation.ui.compose.screen.home.common.user

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.size.Size
import com.goforer.base.designsystem.component.IconButton
import com.goforer.base.designsystem.component.IconContainer
import com.goforer.base.designsystem.component.ImageCrossFade
import com.goforer.base.designsystem.component.loadImagePainter
import com.goforer.base.extension.toUserUiState
import com.goforer.phogal.R
import com.goforer.phogal.data.model.remote.response.gallery.common.UserUiState
import com.goforer.phogal.presentation.stateholder.business.home.common.follow.FollowViewModel
import com.goforer.phogal.presentation.stateholder.uistate.home.common.user.UserContainerState
import com.goforer.phogal.presentation.stateholder.uistate.home.common.user.rememberUserContainerState
import com.goforer.phogal.presentation.stateholder.uistate.home.common.user.rememberUserInfoState
import com.goforer.phogal.presentation.ui.compose.screen.home.common.follow.ShowFollowButton
import com.goforer.phogal.presentation.ui.theme.Black
import com.goforer.phogal.presentation.ui.theme.Blue50
import com.goforer.phogal.presentation.ui.theme.DarkGreen60
import com.goforer.phogal.presentation.ui.theme.DarkGreenGray99
import com.goforer.phogal.presentation.ui.theme.PhogalTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserContainer(
    modifier: Modifier = Modifier,
    state: UserContainerState = rememberUserContainerState(),
    followViewModel: FollowViewModel = hiltViewModel(),
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onShowSnackBar: (text: String) -> Unit,
    onOpenWebView: (firstName: String, url: String) -> Unit
) {
    val userUiState = state.userState.value.toUserUiState()
    val lastName = userUiState.last_name ?: stringResource(id = R.string.picture_no_last_name)
    var showUserInfoBottomSheet by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier.background(state.colorsState.value[2]),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp)
                .background(Color.Transparent)
                .wrapContentHeight(Alignment.CenterVertically)
                .fillMaxWidth()
                .heightIn(76.dp, 114.dp)
                .clickable {
                    showUserInfoBottomSheet = true
                },
        ) {
            ShowProfileImage(
                profileImageSize = state.profileSizeState.value.dp,
                userUiState = userUiState,
                lastName = lastName,
                visibleViewPhotosButton = state.visibleViewButtonState.value,
                onViewPhotos = onViewPhotos
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier
                .height(IntrinsicSize.Min)
                .widthIn(186.dp)
            ) {
                Text(
                    text = userUiState.name,
                    color = state.colorsState.value[0],
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Normal,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "${userUiState.total_likes}${" "}" +
                            "${stringResource(id = R.string.picture_likes)}${" "}${userUiState.total_collections}${" "}" +
                            "${stringResource(id = R.string.picture_collections)}${" "}",
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Normal,
                    color = state.colorsState.value[1],
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "${stringResource(id = R.string.user_updated_at)}${" "}${userUiState.updated_at}",
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Normal,
                    color = state.colorsState.value[1],
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Spacer(modifier = Modifier.width(10.dp))
            ShowFollowButton(
                modifier = modifier,
                followColor = state.colorsState.value[4],
                followViewModel.isUserFollowed(userUiState)
            ) {
                followViewModel.setUserFollow(userUiState)
            }
        }

        if (state.visibleViewButtonState.value) {
            Text(
                "${stringResource(id = R.string.picture_view_photos)}${" "}${userUiState.total_photos}${" "}${stringResource(id = R.string.picture_photos, userUiState.name)}",
                modifier = Modifier
                    .padding(start = if (state.fromItemState.value)
                        56.dp
                    else
                        66.dp)
                    .clickable {
                        onViewPhotos(userUiState.username, userUiState.first_name, lastName, userUiState.username)
                    },
                color = if (state.fromItemState.value)
                    Color.White
                else
                    DarkGreen60,
                fontFamily = FontFamily.SansSerif,
                fontSize = 13.sp,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

    if (showUserInfoBottomSheet) {
        val text = stringResource(id = R.string.user_info_has_no_portfolio)

        UserInfoBottomSheet(
            userInfoState = rememberUserInfoState(),
            userUiState = userUiState,
            showUserInfoBottomSheet = showUserInfoBottomSheet,
            onDismissedRequest = {
                showUserInfoBottomSheet = false
                if (it) {
                    if (userUiState.portfolio_url.isNullOrEmpty()) {
                        state.baseUiState.scope.launch {
                            onShowSnackBar("${userUiState.first_name}${" "}${text}")
                        }
                    } else {
                        onOpenWebView(userUiState.first_name, userUiState.portfolio_url)
                    }
                }
            }
        )
    }
}

@Composable
fun ShowProfileImage(
    profileImageSize: Dp,
    userUiState: UserUiState,
    lastName: String,
    visibleViewPhotosButton: Boolean,
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
) {
    IconContainer(profileImageSize) {
        Box {
            val painter = loadImagePainter(
                data = userUiState.profile_image.small,
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
                        if (visibleViewPhotosButton)
                            onViewPhotos(
                                userUiState.username,
                                userUiState.first_name,
                                lastName,
                                userUiState.username
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowPortfolioButton(
    scope: CoroutineScope,
    bottomSheetState: SheetState,
    openBottomSheetState: MutableState<Boolean>,
    firstName: String,
    onDismissedRequest: (Boolean) -> Unit
) {
    IconButton(
        modifier = Modifier.padding(horizontal = 2.dp),
        height = 32.dp,
        onClick = {
            scope.launch {
                bottomSheetState.hide()
            }.invokeOnCompletion {
                if (!bottomSheetState.isVisible) {
                    openBottomSheetState.value = false
                }
            }

            onDismissedRequest(true)
        },
        icon = {
            Icon(
                imageVector = Icons.Default.OpenInBrowser,
                contentDescription = null,
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.user_info_portfolio, firstName),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable {},
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
                    .heightIn(76.dp, 114.dp)
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
                    .height(IntrinsicSize.Min)
                    .widthIn(186.dp)
                ) {
                    Text(
                        text = "Lukoh",
                        color = Black,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Normal,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "${39}${" "}" +
                                "${stringResource(id = R.string.picture_likes)}${" "}${30}${" "}" +
                                "${stringResource(id = R.string.picture_collections)}${" "}",
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Normal,
                        color = Black,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "${stringResource(id = R.string.user_updated_at)}${" "}${"2023-06-12"}",
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Normal,
                        color = Black,
                        style = MaterialTheme.typography.titleSmall
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))
                Button(
                    onClick = {},
                    modifier = Modifier
                        .widthIn(88.dp)
                        .heightIn(42.dp)
                        .indication(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false)
                        ),
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Transparent
                    ),
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            modifier = Modifier.size(width = 28.dp, height = 28.dp),
                            imageVector = Icons.Filled.Add,
                            contentDescription = "F",
                            tint = Blue50
                        )
                        Spacer(modifier = Modifier.width(width = 4.dp))
                        Text(
                            text = "F",
                            color = Blue50,
                            fontStyle = FontStyle.Normal,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            Text(
                "${stringResource(id = R.string.picture_view_photos)}${" "}${77}${" "}${stringResource(id = R.string.picture_photos, "Lukoh")}",
                modifier = Modifier
                    .padding(start = 56.dp)
                    .clickable {},
                color = Color.White,
                fontFamily = FontFamily.SansSerif,
                fontSize = 13.sp,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}