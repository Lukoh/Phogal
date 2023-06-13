package com.goforer.phogal.presentation.ui.compose.screen.home.setting.bookmark

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.goforer.phogal.R
import com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo.Picture
import com.goforer.phogal.presentation.stateholder.business.home.common.gallery.bookmark.BookmarkViewModel
import com.goforer.phogal.presentation.ui.compose.screen.home.common.InitScreen

@Composable
fun BookmarkedPhotosContent(
    modifier: Modifier = Modifier,
    bookmarkViewModel: BookmarkViewModel = hiltViewModel(),
    contentPadding: PaddingValues = PaddingValues(4.dp),
    enabledLoadPhotosState: MutableState<Boolean>,
    onItemClicked: (item: Picture, index: Int) -> Unit
) {
    val bookmarkedPictures = bookmarkViewModel.getBookmarkPictures()

    if (!bookmarkedPictures.isNullOrEmpty()) {
        BookmarkedPhotosSection(
            modifier = modifier,
            contentPadding = contentPadding,
            photos = bookmarkedPictures,
            onItemClicked = onItemClicked
        )
    } else {
        if (enabledLoadPhotosState.value) {
            BoxWithConstraints(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                InitScreen(
                    modifier = Modifier,
                    text = stringResource(id = R.string.setting_no_bookmarked_photos)
                )
            }
        }
    }
}