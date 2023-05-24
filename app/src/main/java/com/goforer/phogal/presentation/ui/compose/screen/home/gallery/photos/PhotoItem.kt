package com.goforer.phogal.presentation.ui.compose.screen.home.gallery.photos

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.size.Size
import com.goforer.base.designsystem.component.loadImagePainter
import com.goforer.phogal.data.model.remote.response.gallery.photos.Photo
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray2
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import java.lang.Float.min

@Composable
fun PhotoItem(
    modifier: Modifier = Modifier,
    index: Int,
    photo: Photo,
    onItemClicked: (item: Photo, index: Int) -> Unit
) {
    var isClicked by rememberSaveable { mutableStateOf(false) }
    val verticalPadding = if (index == 0)
        2.dp
    else
        4.dp

    photo.alreadySearched = true
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
            defaultElevation = 8.dp,
            pressedElevation = 2.dp,
            focusedElevation = 4.dp
        )
    ) {
        val imageUrl = photo.urls.regular
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
                    isClicked = true
                    onItemClicked.invoke(photo, index)
                }
                .scale(.8f + (.2f * transition))
                .graphicsLayer { rotationX = (1f - transition) * 5f }
                .alpha(min(1f, transition / .2f))

            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = imageModifier,
                colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(transition) })
            )
            UserContainer(modifier = Modifier, photo.user)
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
fun PhotoItemPreview(modifier: Modifier = Modifier) {
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