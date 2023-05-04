package com.goforer.phogal.presentation.ui.compose.screen.home.photo

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.size.Size
import com.goforer.base.ui.compose.loadImagePainter
import com.goforer.phogal.data.model.response.Document
import com.goforer.phogal.presentation.stateholder.uistate.home.photos.PhotoItemState
import com.goforer.phogal.presentation.stateholder.uistate.home.photos.rememberPhotoItemState
import com.goforer.phogal.presentation.ui.theme.Black
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray10
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray2
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun PhotoItem(
    modifier: Modifier = Modifier,
    index: Int,
    document: Document,
    onItemClicked: (item: Document, index: Int) -> Unit,
    state: PhotoItemState = rememberPhotoItemState()
) {
    /*
     * The following code implements the requirement of advancing automatically
     * to the DetailInfo screen when person id is changed....
     * and the user wanted to continue with the next process.
     */
    /*
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val currentNavigateToDetailInfo by rememberUpdatedState(onNavigateToDetailInfo)
    var clikced by remember { mutableStateOf(false) }

    LaunchedEffect(clikced, lifecycle) {
        if (clikced) {
            snapshotFlow { person.id }
                .flowWithLifecycle(lifecycle)
                .collect {
                    currentNavigateToDetailInfo(it)
                }
        }
    }

     */

    val verticalPadding = if (index == 0)
        2.dp
    else
        4.dp

    state.heightDpState.value = animateDpAsState(
        targetValue = 68.dp
    ).value
    Surface(modifier = modifier.fillMaxWidth()) {
        BoxWithConstraints(
            modifier = modifier
            .padding(0.dp, verticalPadding)
            .clip(RoundedCornerShape(4.dp))
        ) {
            val imageUrl = document.image_url ?: document.thumbnail
            val painter = loadImagePainter(
                data = imageUrl!!,
                size = Size.ORIGINAL
            )

            if (painter.state is AsyncImagePainter.State.Loading) {
                val holderModifier = Modifier
                    .fillMaxWidth()
                    .height(175.dp)
                    .align(Alignment.Center)
                    .border(BorderStroke(1.dp, Black))
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
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .border(BorderStroke(1.dp, Black))
                    .background(ColorSystemGray10)
                    .clip(RoundedCornerShape(4.dp))
                    .clickable { onItemClicked.invoke(document, index) }

                Image(
                    modifier = imageModifier,
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}