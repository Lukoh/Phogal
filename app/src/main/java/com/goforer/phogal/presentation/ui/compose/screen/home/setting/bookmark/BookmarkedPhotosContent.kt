package com.goforer.phogal.presentation.ui.compose.screen.home.setting.bookmark

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo.Picture
import com.goforer.phogal.presentation.stateholder.business.home.common.gallery.bookmark.BookmarkViewModel

@Composable
fun BookmarkedPhotosContent(
    modifier: Modifier = Modifier,
    bookmarkViewModel: BookmarkViewModel = hiltViewModel(),
    contentPadding: PaddingValues = PaddingValues(4.dp),
    onItemClicked: (item: Picture, index: Int) -> Unit
) {
    bookmarkViewModel.getBookmarkPictures()?.let {
        BookmarkedPhotosSection(
            modifier = modifier,
            contentPadding = contentPadding,
            photos = it,
            onItemClicked = onItemClicked
        )
    }
}