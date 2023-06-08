package com.goforer.phogal.presentation.ui.compose.screen.home.setting.bookmark

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.goforer.base.storage.LocalStorage
import com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo.Picture

@Composable
fun BookmarkedPhotosContent(
    modifier: Modifier = Modifier,
    storage: LocalStorage,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    onItemClicked: (item: Picture, index: Int) -> Unit
) {
    BookmarkedPhotosSection(
        modifier = modifier,
        contentPadding = contentPadding,
        photos = storage.geBookmarkedPhotos()!!,
        onItemClicked = onItemClicked
    )
}