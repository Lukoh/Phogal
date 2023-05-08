package com.goforer.phogal.presentation.ui.compose.screen.home.photo

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.size.Size
import com.goforer.base.ui.compose.loadImagePainter
import com.goforer.phogal.data.model.response.Document
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
    onItemClicked: (item: Document, index: Int) -> Unit
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

    Card(
        modifier = modifier.padding(0.dp, verticalPadding),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        val imageUrl = document.image_url ?: document.thumbnail
        val painter = loadImagePainter(
            data = imageUrl!!,
            size = Size.ORIGINAL
        )

        if (painter.state is AsyncImagePainter.State.Loading) {
            val holderModifier = Modifier
                .fillMaxWidth()
                .height(256.dp)
                .align(Alignment.CenterHorizontally)
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

    Card(
        modifier = modifier.padding(0.dp, verticalPadding),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        val imageUrl = "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg"
        val painter = loadImagePainter(
            data = imageUrl,
            size = Size.ORIGINAL
        )

        val imageModifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(ColorSystemGray10)
            .clip(RoundedCornerShape(4.dp))
            .clickable { }

        Image(
            modifier = imageModifier,
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}