package com.goforer.phogal.presentation.ui.compose.screen.home.setting.bookmark

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.goforer.phogal.data.model.remote.response.gallery.photo.Picture

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookmarkedPhotosSection(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    photos: MutableList<Picture>,
    onItemClicked: (item: Picture, index: Int) -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .padding(
                0.dp,
                contentPadding.calculateTopPadding(),
                0.dp,
                0.dp
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
        ) {
            itemsIndexed(
                items = photos,
                key = { _, item -> item.id }
            ) {index, item ->
                PictureItem(
                    modifier = modifier.animateItemPlacement(
                        tween(durationMillis = 250)
                    ),
                    index = index,
                    picture = item,
                    visibleViewPhotosButton = false,
                    onItemClicked = onItemClicked,
                    onViewPhotos = { _, _, _, _ -> },
                    onShowSnackBar = {}
                )
                
                if (index == photos.size - 1)
                    Spacer(modifier = Modifier.height(26.dp))
            }
        }
    }
}