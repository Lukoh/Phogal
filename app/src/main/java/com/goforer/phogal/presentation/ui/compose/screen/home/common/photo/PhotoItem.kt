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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.size.Size
import com.goforer.base.designsystem.component.loadImagePainter
import com.goforer.phogal.R
import com.goforer.phogal.data.model.remote.response.gallery.common.Photo
import com.goforer.phogal.presentation.stateholder.uistate.home.common.user.rememberUserContainerState
import com.goforer.phogal.presentation.stateholder.uistate.home.common.photo.PhotoItemState
import com.goforer.phogal.presentation.stateholder.uistate.home.common.photo.rememberPhotoItemState
import com.goforer.phogal.presentation.ui.compose.screen.home.common.user.UserContainer
import com.goforer.phogal.presentation.ui.theme.Blue50
import com.goforer.phogal.presentation.ui.theme.Blue70
import com.goforer.phogal.presentation.ui.theme.Blue75
import com.goforer.phogal.presentation.ui.theme.ColorSnowWhite
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray7
import com.goforer.phogal.presentation.ui.theme.Red60Transparent
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun PhotoItem(
    modifier: Modifier = Modifier,
    state: PhotoItemState = rememberPhotoItemState(),
    onItemClicked: (item: Photo, index: Int) -> Unit,
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onShowSnackBar: (text: String) -> Unit,
    onOpenWebView: (firstName: String, url: String) -> Unit
) {
    val photo = state.photoState.value as Photo

    photo.alreadySearched = true
    AnimatedVisibility(
        visible = true,
        modifier = modifier,
        enter = scaleIn(transformOrigin = TransformOrigin(0f, 0f)) +
                fadeIn() + expandIn(expandFrom = Alignment.TopStart),
        exit = scaleOut(transformOrigin = TransformOrigin(0f, 0f)) +
                fadeOut() + shrinkOut(shrinkTowards = Alignment.TopStart)
    ) {
        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(
                contentColor = MaterialTheme.colorScheme.primary,
                containerColor =
                if (state.clickedState.value)
                    MaterialTheme.colorScheme.primaryContainer
                else
                    MaterialTheme.colorScheme.surfaceVariant,
                disabledContentColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.onSurface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp,
                pressedElevation = 2.dp,
                focusedElevation = 4.dp
            )
        ) {
            val imageUrl = photo.urls.raw
            val painter = loadImagePainter(
                data = imageUrl,
                size = Size(photo.width.div(8), photo.height.div(8))
            )
            val transition by animateFloatAsState(
                targetValue = if (painter.state is AsyncImagePainter.State.Success) 1f else 0f
            )

            if (painter.state is AsyncImagePainter.State.Loading) {
                val holderModifier = Modifier
                    .fillMaxWidth()
                    .height(256.dp)
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
                val imageModifier = Modifier
                    .then(
                        ((painter.state as? AsyncImagePainter.State.Success)
                            ?.painter
                            ?.intrinsicSize
                            ?.let { intrinsicSize ->
                                Modifier.aspectRatio(intrinsicSize.width / intrinsicSize.height)
                            } ?: Modifier)
                    )
                    .clip(RoundedCornerShape(4.dp))
                    .clickable {
                        state.clickedState.value = true
                        onItemClicked.invoke(photo, state.indexState.value)
                    }
                    .scale(.8f + (.2f * transition))
                    .graphicsLayer { rotationX = (1f - transition) * 5f }
                    .alpha(transition / .2f)

                Box {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = imageModifier,
                        colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(transition) })
                    )

                    if (state.bookmarkedState.value) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_bookmark_on),
                            contentDescription = "Bookmark",
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 8.dp, end = 16.dp),
                            tint = Red60Transparent
                        )
                    }
                }

                UserContainer(
                    modifier = Modifier,
                    state = rememberUserContainerState(
                        userState = rememberSaveable { mutableStateOf(photo.user.toString()) },
                        profileSizeState = rememberSaveable { mutableDoubleStateOf(36.0) },
                        colors = rememberSaveable { listOf(Color.White, Color.White, Blue70, Blue75, Blue50, ColorSnowWhite) },
                        visibleViewButtonState = state.visibleViewButtonState,
                        fromItemState = rememberSaveable { mutableStateOf(true) }
                    ),
                    onViewPhotos = onViewPhotos,
                    onShowSnackBar = onShowSnackBar,
                    onOpenWebView = onOpenWebView
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
fun PhotosItemPreview(modifier: Modifier = Modifier) {
    val verticalPadding = 4.dp
    var isClicked by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier.padding(0.dp, verticalPadding),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.primary,
            containerColor =
            if (isClicked)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8. dp,
            pressedElevation = 2. dp,
            focusedElevation = 4. dp
        )
    ) {
        val imageUrl = "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg"
        val painter = loadImagePainter(
            data = imageUrl,
            size = Size.ORIGINAL
        )

        val imageModifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(4.dp))
            .clickable { isClicked = true }

        Image(
            modifier = imageModifier,
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}