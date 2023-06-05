package com.goforer.phogal.presentation.ui.compose.screen.home.gallery.photo

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.size.Size
import com.goforer.base.designsystem.component.loadImagePainter
import com.goforer.phogal.R
import com.goforer.phogal.data.model.remote.response.gallery.photo.Exif
import com.goforer.phogal.data.model.remote.response.gallery.photo.Picture
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.data.network.response.Resource
import com.goforer.phogal.data.network.response.Status
import com.goforer.phogal.presentation.analytics.TrackScreenViewEvent
import com.goforer.phogal.presentation.stateholder.business.home.gallery.photo.PictureViewModel
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.common.rememberUserContainerState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.photo.PhotoContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.photo.rememberPhotoContentState
import com.goforer.phogal.presentation.ui.compose.screen.home.gallery.common.ErrorContent
import com.goforer.phogal.presentation.ui.compose.screen.home.gallery.common.UserContainer
import com.goforer.phogal.presentation.ui.theme.Black
import com.goforer.phogal.presentation.ui.theme.ColorBlackLight
import com.goforer.phogal.presentation.ui.theme.ColorSnowWhite
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray1
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray7
import com.goforer.phogal.presentation.ui.theme.ColorText4
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun PictureContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    id : String,
    state: PhotoContentState = rememberPhotoContentState(),
    pictureViewModel: PictureViewModel = hiltViewModel(),
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onShowSnackBar: (text: String) -> Unit,
    onShownPhoto: (picture: Picture) -> Unit
) {
    if (state.enabledLoadPhotos.value) {
        state.enabledLoadPhotos.value = false
        pictureViewModel.trigger(2, Params(id))
    }

    val pictureUiState = pictureViewModel.pictureUiState.collectAsStateWithLifecycle()

    if (pictureUiState.value is Resource) {
        val resource = pictureUiState.value as Resource
        when(resource.status) {
            Status.SUCCESS -> {
                val picture = resource.data as Picture

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(top = contentPadding.calculateTopPadding())
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val verticalPadding = 2.dp

                        Card(
                            modifier = modifier.padding(0.dp, verticalPadding),
                            colors = CardDefaults.cardColors(
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                containerColor =  MaterialTheme.colorScheme.onPrimary,
                                disabledContentColor = MaterialTheme.colorScheme.surface,
                                disabledContainerColor = MaterialTheme.colorScheme.onSurface
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.dp,
                                pressedElevation = 2.dp,
                                focusedElevation = 4.dp
                            ),
                            shape = RectangleShape
                        ) {
                            val imageUrl = picture.urls.raw
                            val painter = loadImagePainter(
                                data = imageUrl,
                                size = Size(picture.width.div(8), picture.height.div(8))
                            )
                            val transition by animateFloatAsState(
                                targetValue = if (painter.state is AsyncImagePainter.State.Success) 1f else 0f
                            )

                            if (painter.state is AsyncImagePainter.State.Loading) {
                                val holderModifier = Modifier
                                    .fillMaxWidth()
                                    .height(LocalConfiguration.current.screenHeightDp.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .background(ColorSystemGray7)
                                    .placeholder(
                                        visible = true,
                                        highlight = PlaceholderHighlight.fade(),
                                    )

                                Text(
                                    modifier = holderModifier,
                                    text = "",
                                    textAlign = TextAlign.Center
                                )
                            } else {
                                UserContainer(
                                    modifier = Modifier,
                                    user = picture.user,
                                    state = rememberUserContainerState(
                                        profileSize = rememberSaveable { mutableDoubleStateOf(48.0) },
                                        colors = rememberSaveable { listOf(ColorSystemGray1, ColorSystemGray1, ColorSnowWhite) },
                                        visibleViewPhotosButton = rememberSaveable { mutableStateOf(state.visibleViewPhotosButton.value) }
                                    ),
                                    onViewPhotos = onViewPhotos,
                                    onShowSnackBar = onShowSnackBar
                                )

                                AnimatedVisibility(
                                    visible = true,
                                    modifier = modifier,
                                    enter = scaleIn(transformOrigin = TransformOrigin(0f, 0f)) +
                                            fadeIn() + expandIn(expandFrom = Alignment.TopStart),
                                    exit = scaleOut(transformOrigin = TransformOrigin(0f, 0f)) +
                                            fadeOut() + shrinkOut(shrinkTowards = Alignment.TopStart)
                                ) {
                                    Image(
                                        painter = painter,
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .then(((painter.state as? AsyncImagePainter.State.Success)
                                                ?.painter
                                                ?.intrinsicSize
                                                ?.let { intrinsicSize ->
                                                    Modifier.aspectRatio(intrinsicSize.width / intrinsicSize.height)
                                                } ?: Modifier)
                                            )
                                            .clip(RectangleShape)
                                            .clickable {
                                            }
                                            .scale(.8f + (.2f * transition))
                                            .graphicsLayer { rotationX = (1f - transition) * 5f }
                                            .alpha(transition / .2f),
                                        colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                                            setToSaturation(
                                                transition
                                            )
                                        })
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))
                                BehaviorItem(picture.likes, picture.downloads, picture.views)
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = picture.description ?: picture.alt_description
                                    ?: stringResource(id = R.string.picture_no_description),
                                    modifier = Modifier.padding(8.dp, 4.dp),
                                    color = ColorText4,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.W400,
                                    fontSize = 18.sp,
                                    fontStyle = FontStyle.Normal,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                                picture.location?.let {
                                    LocationItem(it.name)
                                    Spacer(modifier = Modifier.height(16.dp))
                                }

                                DateItem(picture.created_at)
                                Spacer(modifier = Modifier.height(16.dp))

                                picture.exif?.let { exif ->
                                    ExifItem(exif)
                                }

                                Spacer(modifier = Modifier.height(30.dp))
                                onShownPhoto(picture)
                            }
                        }

                        Spacer(modifier = Modifier.height(30.dp))
                    }
                }

                TrackScreenViewEvent(screenName = "PhotoContent")
            }
            Status.LOADING -> {
                AnimatedVisibility(
                    visible = true,
                    modifier = Modifier,
                    enter = scaleIn(transformOrigin = TransformOrigin(0f, 0f)) +
                            fadeIn() + expandIn(expandFrom = Alignment.TopStart),
                    exit = scaleOut(transformOrigin = TransformOrigin(0f, 0f)) +
                            fadeOut() + shrinkOut(shrinkTowards = Alignment.TopStart)
                ) {
                    LoadingPicture(
                        modifier = Modifier.padding(4.dp, 4.dp),
                        enableLoadIndicator = true
                    )
                }
            }
            Status.ERROR-> {
                AnimatedVisibility(
                    visible = true,
                    modifier = Modifier,
                    enter = scaleIn(transformOrigin = TransformOrigin(0f, 0f)) +
                            fadeIn() + expandIn(expandFrom = Alignment.TopStart),
                    exit = scaleOut(transformOrigin = TransformOrigin(0f, 0f)) +
                            fadeOut() + shrinkOut(shrinkTowards = Alignment.TopStart)
                ) {
                    ErrorContent(
                        modifier = Modifier,
                        title = if (resource.errorCode !in 200..299)
                            stringResource(id = R.string.error_dialog_network_title)
                        else
                            stringResource(id = R.string.error_dialog_title),
                        message = "${stringResource(id = R.string.error_get_picture)}${"\n\n"}${resource.message.toString()}",
                        onRetry = {
                            pictureViewModel.trigger(2, Params(id))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BehaviorItem(likes: Int, downloads: Int, views: Int) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${likes}${" "}${stringResource(id = R.string.picture_likes)}",
            modifier = Modifier.padding(8.dp, 4.dp),
            color = Black,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            fontStyle = FontStyle.Normal,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = "${downloads}${" "}${stringResource(id = R.string.picture_downloads)}",
            modifier = Modifier.padding(8.dp, 4.dp),
            color = Black,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            fontStyle = FontStyle.Normal,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = "${views}${" "}${stringResource(id = R.string.picture_views)}",
            modifier = Modifier.padding(8.dp, 4.dp),
            color = Black,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            fontStyle = FontStyle.Normal,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun LocationItem(location: String?) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_location),
            contentDescription = "Location",
            modifier = Modifier
                .size(22.dp)
                .padding(horizontal = 4.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = location ?: stringResource(id = R.string.picture_no_location),
            color = ColorBlackLight,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            fontStyle = FontStyle.Normal,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun DateItem(createdAt: String) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_date),
            contentDescription = "Date",
            modifier = Modifier
                .size(22.dp)
                .padding(horizontal = 4.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "${createdAt}${" "}${stringResource(id = R.string.picture_posted)}",
            color = ColorBlackLight,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            fontStyle = FontStyle.Normal,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun ExifItem(exif: Exif) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_camera),
            contentDescription = "Exif",
            modifier = Modifier
                .size(22.dp)
                .padding(horizontal = 4.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = exif.name ?: stringResource(id = R.string.picture_no_camera_name),
            color = ColorBlackLight,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            fontStyle = FontStyle.Normal,
            style = MaterialTheme.typography.titleMedium
        )
    }

    exif.name?.let {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${"Lens\n"}${"f/"}${exif.aperture}${"  "}${exif.focal_length}${"mm  "}${exif.exposure_time}${"s  iso "}${exif.iso}",
            modifier = Modifier.padding(horizontal = 28.dp),
            color = ColorBlackLight,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
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
fun PictureContentPreview(modifier: Modifier = Modifier) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val verticalPadding = 2.dp

            Card(
                modifier = modifier.padding(0.dp, verticalPadding),
                colors = CardDefaults.cardColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor =  MaterialTheme.colorScheme.onPrimary,
                    disabledContentColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.onSurface
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 2.dp,
                    focusedElevation = 4.dp
                ),
                shape = RectangleShape
            ) {
                val imageUrl = "https://images.unsplash.com/face-springmorning.jpg"
                val painter = loadImagePainter(
                    data = imageUrl,
                    size = Size(420, 640)
                )
                val transition by animateFloatAsState(
                    targetValue = if (painter.state is AsyncImagePainter.State.Success) 1f else 0f
                )

                val imageModifier = Modifier
                    .width(420.dp)
                    .height(640.dp)
                    .clip(RectangleShape)

                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = imageModifier,
                    colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(transition) })
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${"220"}${" "}${stringResource(id = R.string.picture_likes)}",
                        modifier = Modifier.padding(8.dp, 4.dp),
                        color = Black,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "${"400"}${" "}${stringResource(id = R.string.picture_downloads)}",
                        modifier = Modifier.padding(8.dp, 4.dp),
                        color = Black,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "${"450"}${" "}${stringResource(id = R.string.picture_views)}",
                        modifier = Modifier.padding(8.dp, 4.dp),
                        color = Black,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Beautiful Fresh Awesome Forest",
                    modifier = Modifier.padding(8.dp, 4.dp),
                    color = ColorText4,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.W400,
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Normal,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = "Location",
                        modifier = Modifier
                            .size(22.dp)
                            .padding(horizontal = 4.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Seoul, Secho-Gu, Korea",
                        color = ColorBlackLight,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Normal,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_date),
                        contentDescription = "Date",
                        modifier = Modifier
                            .size(22.dp)
                            .padding(horizontal = 4.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${"2023-05-28"}${" "}${stringResource(id = R.string.picture_posted)}",
                        color = ColorBlackLight,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Normal,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = "Exif",
                        modifier = Modifier
                            .size(22.dp)
                            .padding(horizontal = 4.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(id = R.string.picture_no_camera_name),
                        color = ColorBlackLight,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Normal,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${"Lens\n"}${"f/"}${15.1}${"  "}${30.0}${"mm  "}${300}${"s  iso "}${300}",
                    modifier = Modifier.padding(horizontal = 28.dp),
                    color = ColorBlackLight,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Normal,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(30.dp))
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}