package com.goforer.phogal.presentation.ui.compose.screen.home.setting.bookmark

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goforer.phogal.R
import com.goforer.phogal.data.model.remote.response.gallery.common.PhotoUiState
import com.goforer.phogal.presentation.stateholder.uistate.home.setting.bookmark.BookmarkedPhotosState
import com.goforer.phogal.presentation.stateholder.uistate.home.setting.bookmark.rememberBookmarkedPhotosState
import com.goforer.phogal.presentation.ui.compose.screen.home.common.InitScreen

@Composable
fun BookmarkedPhotosContent(
    modifier: Modifier = Modifier,
    state: BookmarkedPhotosState = rememberBookmarkedPhotosState(),
    contentPadding: PaddingValues = PaddingValues(4.dp),
    onTriggered: (enabled: Boolean) -> Unit,
    onItemClicked: (photoUiState: PhotoUiState, index: Int) -> Unit,
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onOpenWebView: (firstName: String, url: String) -> Unit
) {
    val triggered by rememberUpdatedState(onTriggered)
    val photos by state.uiState.collectAsStateWithLifecycle()

    triggered(state.enabledLoadState.value)
    state.enabledLoadState.value = false
    if (photos.isNotEmpty()) {
        BookmarkedPhotosSection(
            modifier = modifier,
            contentPadding = contentPadding,
            photos = photos,
            onItemClicked = onItemClicked,
            onViewPhotos = onViewPhotos,
            onOpenWebView = onOpenWebView
        )
    }

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