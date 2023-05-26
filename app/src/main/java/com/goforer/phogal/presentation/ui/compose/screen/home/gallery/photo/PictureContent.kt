package com.goforer.phogal.presentation.ui.compose.screen.home.gallery.photo

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.size.Size
import com.goforer.base.designsystem.component.loadImagePainter
import com.goforer.phogal.R
import com.goforer.phogal.data.model.remote.response.gallery.photo.Picture
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.data.network.response.Resource
import com.goforer.phogal.data.network.response.Status
import com.goforer.phogal.presentation.stateholder.business.home.gallery.photo.PictureViewModel
import com.goforer.phogal.presentation.ui.compose.screen.home.gallery.common.ErrorContent
import com.goforer.phogal.presentation.ui.compose.screen.home.gallery.common.UserContainer
import com.goforer.phogal.presentation.ui.theme.ColorSnowWhite
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray1
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray2
import com.goforer.phogal.presentation.ui.theme.ColorText4
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import java.lang.Float

@Composable
fun PictureContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    id : String,
    pictureViewModel: PictureViewModel = hiltViewModel(),
) {
    pictureViewModel.trigger(2, Params(id))

    val pictureUiState = pictureViewModel.pictureUiState.collectAsStateWithLifecycle()

    pictureViewModel.trigger(2, Params(id))
    if (pictureUiState.value is Resource) {
        val resource = pictureUiState.value as Resource
        when(resource.status) {
            Status.SUCCESS -> {
                val picture = resource.data as Picture

                BoxWithConstraints(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(
                                0.dp,
                                contentPadding.calculateTopPadding(),
                                0.dp,
                                0.dp
                            )
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val verticalPadding = 2.dp

                        Card(
                            modifier = modifier.padding(0.dp, verticalPadding).fillMaxSize(),
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
                                    .background(ColorSystemGray2)
                                    .placeholder(
                                        visible = true,
                                        highlight = PlaceholderHighlight.shimmer(),
                                    )

                                Text(
                                    modifier = holderModifier,
                                    text = "",
                                    textAlign = TextAlign.Center
                                )
                            } else {
                                UserContainer(modifier = Modifier, picture.user, 48.dp, ColorSystemGray1, ColorSystemGray1, ColorSnowWhite)

                                val imageModifier = Modifier
                                    .then(
                                        ((painter.state as? AsyncImagePainter.State.Success)
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
                                    .alpha(Float.min(1f, transition / .2f))

                                Image(
                                    painter = painter,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = imageModifier,
                                    colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(transition) })
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = picture.description ?: picture.alt_description,
                                    modifier = Modifier.padding(8.dp, 4.dp),
                                    color = ColorText4,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp,
                                    fontStyle = FontStyle.Normal,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
            }
            Status.LOADING -> {
                LoadingPicture(
                    modifier = Modifier.padding(4.dp, 4.dp),
                    enableLoadIndicator = true
                )
            }
            Status.ERROR-> {
                ErrorContent(
                    title = stringResource(id = R.string.error_dialog_title),
                    message = "${stringResource(id = R.string.error_get_picture)}${"\n\n"}${resource.message.toString()}",
                    onRetry = {
                        pictureViewModel.trigger(2, Params(id))
                    }
                )
            }
        }
    }
}