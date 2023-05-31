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
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImagePainter
import coil.size.Size
import com.goforer.base.designsystem.component.IconButton
import com.goforer.base.designsystem.component.IconContainer
import com.goforer.base.designsystem.component.ImageCrossFade
import com.goforer.base.designsystem.component.loadImagePainter
import com.goforer.phogal.R
import com.goforer.phogal.data.model.remote.response.gallery.common.User
import com.goforer.phogal.presentation.ui.theme.DarkGreen60
import com.goforer.phogal.presentation.ui.theme.PhogalTheme

@Composable
fun UserContainer(
    modifier: Modifier = Modifier,
    user: User,
    profileSize: Dp,
    firstTextColor: Color,
    secondTextColor: Color,
    backgroundColor: Color,
    visibleViewPhotosButton: Boolean,
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
) {
    val lastName = user.last_name ?: stringResource(id = R.string.picture_no_last_name)

    Column(
        modifier = modifier.background(backgroundColor),
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
                .clickable {},
        ) {

            IconContainer(profileSize) {
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
                                if (visibleViewPhotosButton)
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
                    color = firstTextColor,
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
                    color = secondTextColor,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }

        if (visibleViewPhotosButton) {
            IconButton(
                32.dp,
                modifier = Modifier.padding(start = 56.dp, top = 0.dp, bottom = 2.dp),
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
                        fontSize = 11.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
            )
        }
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
                32.dp,
                modifier = Modifier.padding(start = 56.dp, top = 0.dp, bottom = 2.dp),
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
                        fontSize = 10.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
            )
        }
    }
}