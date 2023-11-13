package com.goforer.phogal.presentation.ui.compose.screen.home.common.photo

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.StartOffsetType
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.size.Size
import com.goforer.base.designsystem.animation.GenericCubicAnimationShape
import com.goforer.base.designsystem.component.IconButton
import com.goforer.base.designsystem.component.loadImagePainter
import com.goforer.phogal.R
import com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo.ExifUiState
import com.goforer.phogal.data.datasource.network.response.Resource
import com.goforer.phogal.data.datasource.network.response.Status
import com.goforer.phogal.data.model.remote.response.gallery.common.PhotoUiState
import com.goforer.phogal.presentation.analytics.TrackScreenViewEvent
import com.goforer.phogal.presentation.stateholder.uistate.home.common.user.rememberUserContainerState
import com.goforer.phogal.presentation.stateholder.uistate.home.common.photo.PhotoContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.common.photo.rememberPhotoContentState
import com.goforer.phogal.presentation.ui.compose.screen.home.common.error.ErrorContent
import com.goforer.phogal.presentation.ui.compose.screen.home.common.user.UserContainer
import com.goforer.phogal.presentation.ui.theme.Black
import com.goforer.phogal.presentation.ui.theme.Blue75
import com.goforer.phogal.presentation.ui.theme.ColorBlackLight
import com.goforer.phogal.presentation.ui.theme.ColorSnowWhite
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray1
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray5
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray7
import com.goforer.phogal.presentation.ui.theme.ColorText4
import com.goforer.phogal.presentation.ui.theme.DarkGreen60
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun PictureContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    state: PhotoContentState = rememberPhotoContentState(),
    onTriggered: (enabled: Boolean) -> Unit,
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onShowSnackBar: (text: String) -> Unit,
    onShownPhoto: (photoUiState: PhotoUiState) -> Unit,
    onOpenWebView: (firstName: String, url: String) -> Unit,
    onSuccess: (isSuccessful: Boolean) -> Unit
) {
    val triggered by rememberUpdatedState(onTriggered)

    triggered(state.enabledLoadState.value)
    state.enabledLoadState.value = false
    HandlePictureResponse(
        modifier = modifier,
        contentPadding = contentPadding,
        state = state,
        onViewPhotos = onViewPhotos,
        onShowSnackBar = onShowSnackBar,
        onShownPhoto = onShownPhoto,
        onOpenWebView = onOpenWebView,
        onSuccess = onSuccess,
        onRetry = {
            state.enabledLoadState.value = true
            triggered(state.enabledLoadState.value)
            state.enabledLoadState.value = false
        }
    )
}

@Composable
fun HandlePictureResponse(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    state: PhotoContentState = rememberPhotoContentState(),
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onShowSnackBar: (text: String) -> Unit,
    onShownPhoto: (photoUiState: PhotoUiState) -> Unit,
    onOpenWebView: (firstName: String, url: String) -> Unit,
    onSuccess: (isSuccessful: Boolean) -> Unit,
    onRetry: () -> Unit
) {
    val resource by state.uiState.collectAsStateWithLifecycle()

    when(resource.status) {
        Status.SUCCESS -> {
            onSuccess(true)
            HandleSuccess(
                modifier = modifier,
                contentPadding =contentPadding,
                photoUiState = resource.data as PhotoUiState,
                visibleViewPhotosButton = state.visibleViewButtonState.value,
                onViewPhotos = onViewPhotos,
                onShowSnackBar = onShowSnackBar,
                onShownPhoto = onShownPhoto,
                onOpenWebView = onOpenWebView
            )
        }
        Status.LOADING -> {
            HandleLoading()
        }
        Status.ERROR-> {
            onSuccess(false)
            HandleError(
                resource = resource,
                onRetry = onRetry
            )
        }
    }
}

@Composable
fun HandleSuccess(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    photoUiState: PhotoUiState,
    visibleViewPhotosButton: Boolean,
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onShowSnackBar: (text: String) -> Unit,
    onShownPhoto: (photoUiState: PhotoUiState) -> Unit,
    onOpenWebView: (firstName: String, url: String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(top = contentPadding.calculateTopPadding())
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BodyContent(
                modifier = modifier,
                photoUiState = photoUiState,
                visibleViewPhotosButton = visibleViewPhotosButton,
                onViewPhotos = onViewPhotos,
                onShowSnackBar = onShowSnackBar,
                onShownPhoto = onShownPhoto,
                onOpenWebView = onOpenWebView
            )
            Spacer(modifier = Modifier.height(30.dp))
        }
    }

    TrackScreenViewEvent(screenName = "PhotoContent")
}

@Composable
fun HandleLoading() {
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

@Composable
fun HandleError(
    resource: Resource,
    onRetry: () -> Unit
) {
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
            onRetry = onRetry
        )
    }
}

@Composable
fun BodyContent(
    modifier: Modifier = Modifier,
    photoUiState: PhotoUiState,
    visibleViewPhotosButton: Boolean,
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onShowSnackBar: (text: String) -> Unit,
    onShownPhoto: (photoUiState: PhotoUiState) -> Unit,
    onOpenWebView: (firstName: String, url: String) -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.padding(0.dp, 2.dp),
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
        val imageUrl = photoUiState.urls.raw
        val painter = loadImagePainter(
            data = imageUrl,
            size = Size(photoUiState.width.div(8), photoUiState.height.div(8))
        )

        if (painter.state is AsyncImagePainter.State.Loading) {
            val holderModifier = Modifier
                .fillMaxWidth()
                .height(LocalConfiguration.current.screenHeightDp.dp)
                .align(Alignment.CenterHorizontally)
                .background(ColorSystemGray7)
                .placeholder(
                    visible = true,
                    color = MaterialTheme.colorScheme.surface,
                    placeholderFadeTransitionSpec = { tween(durationMillis = 400) },
                    contentFadeTransitionSpec = { tween(durationMillis = 400) },
                    highlight = PlaceholderHighlight.shimmer(
                        animationSpec = infiniteRepeatable(
                            animation = keyframes {
                                durationMillis = 800
                                0f at 0
                                0f at 200
                                1f at 800 with FastOutSlowInEasing
                            },
                            repeatMode = RepeatMode.Reverse,
                            initialStartOffset = StartOffset(
                                offsetType = StartOffsetType.FastForward,
                                offsetMillis = 200
                            )
                        )
                    )
                )

            Text(
                modifier = holderModifier,
                text = "",
                textAlign = TextAlign.Center
            )
        } else {
            UserContainer(
                modifier = Modifier,
                state = rememberUserContainerState(
                    userState = rememberSaveable { mutableStateOf(photoUiState.user.toString()) },
                    profileSizeState = rememberSaveable { mutableDoubleStateOf(48.0) },
                    colorsState = remember { mutableStateOf(listOf(ColorSystemGray1, ColorSystemGray1, ColorSnowWhite, ColorSystemGray5, Blue75, DarkGreen60)) },
                    visibleViewButtonState = rememberSaveable { mutableStateOf(visibleViewPhotosButton) },
                    fromItemState = rememberSaveable { mutableStateOf(false) }
                ),
                onViewPhotos = onViewPhotos,
                onShowSnackBar = onShowSnackBar,
                onOpenWebView = onOpenWebView
            )

            AnimatedVisibility(
                visible = true,
                modifier = modifier,
                enter = scaleIn(transformOrigin = TransformOrigin(0f, 0f)) +
                        fadeIn() + expandIn(expandFrom = Alignment.TopStart),
                exit = scaleOut(transformOrigin = TransformOrigin(0f, 0f)) +
                        fadeOut() + shrinkOut(shrinkTowards = Alignment.TopStart)
            ) {
                ImageContent(painter = painter)
            }

            Spacer(modifier = Modifier.height(12.dp))
            BehaviorItem(photoUiState.likes.toLong(), photoUiState.downloads, photoUiState.views)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = photoUiState.description ?: photoUiState.alt_description
                ?: stringResource(id = R.string.picture_no_description),
                modifier = Modifier.padding(8.dp, 4.dp),
                color = ColorText4,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W400,
                fontSize = 18.sp,
                fontStyle = FontStyle.Normal,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
            photoUiState.location?.let {
                LocationItem(it.name)
                Spacer(modifier = Modifier.height(2.dp))
            }

            DateItem(photoUiState.created_at)
            Spacer(modifier = Modifier.height(2.dp))
            photoUiState.exif?.let { exifUiState ->
                GenericCubicAnimationShape(
                    visible = visible,
                    duration = 550
                ) { animatedShape, _ ->
                    ExifItem(
                        modifier = modifier
                            .padding(horizontal = 4.dp, vertical = 4.dp)
                            .graphicsLayer {
                                clip = true
                                shape = animatedShape
                            },
                        exifUiState = exifUiState
                    )
                }
            }
            IconButton(
                modifier = Modifier.padding(horizontal = 4.dp),
                height = 32.dp,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue75,
                    contentColor = Color.White
                ),
                onClick = {
                    visible = !visible
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Camera,
                        contentDescription = null,
                    )
                },
                text = {
                    Text(
                        text = if (visible)
                            stringResource(id = R.string.picture_close_camera_info)
                        else
                            stringResource(id = R.string.picture_view_camera_info),
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 13.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
            )

            Spacer(modifier = Modifier.height(70.dp))
            onShownPhoto(photoUiState)
        }
    }
}

@Composable
fun ImageContent(
    modifier: Modifier = Modifier,
    painter: AsyncImagePainter
) {
    val transition by animateFloatAsState(
        targetValue = if (painter.state is AsyncImagePainter.State.Success) 1f else 0f, label = ""
    )

    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
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

@Composable
fun BehaviorItem(likes: Long, downloads: Long, views: Long) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "${likes}${" "}${stringResource(id = R.string.picture_likes)}",
            modifier = Modifier.padding(vertical =  4.dp),
            color = Black,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            fontStyle = FontStyle.Normal,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "${downloads}${" "}${stringResource(id = R.string.picture_downloads)}",
            modifier = Modifier.padding(vertical = 4.dp),
            color = Black,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            fontStyle = FontStyle.Normal,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "${views}${" "}${stringResource(id = R.string.picture_views)}",
            modifier = Modifier.padding(vertical = 4.dp),
            color = Black,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            fontStyle = FontStyle.Normal,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun LocationItem(location: String?) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
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
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
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
fun ExifItem(
    modifier: Modifier = Modifier,
    exifUiState: ExifUiState
) {
    Box(modifier) {
        Column {
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
                    text = exifUiState.name ?: stringResource(id = R.string.picture_no_camera_name),
                    color = ColorBlackLight,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Normal,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            exifUiState.name?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${"Lens\n"}${"f/"}${exifUiState.aperture}${"  "}${exifUiState.focal_length}${"mm  "}${exifUiState.exposure_time}${"s  iso "}${exifUiState.iso}",
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
                    targetValue = if (painter.state is AsyncImagePainter.State.Success) 1f else 0f,
                    label = ""
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
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${"220"}${" "}${stringResource(id = R.string.picture_likes)}",
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = Black,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        fontStyle = FontStyle.Normal,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${"400"}${" "}${stringResource(id = R.string.picture_downloads)}",
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = Black,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        fontStyle = FontStyle.Normal,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${"450"}${" "}${stringResource(id = R.string.picture_views)}",
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = Black,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
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
                Spacer(modifier = Modifier.height(16.dp))
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

                Spacer(modifier = Modifier.height(8.dp))
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

                Spacer(modifier = Modifier.height(8.dp))
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