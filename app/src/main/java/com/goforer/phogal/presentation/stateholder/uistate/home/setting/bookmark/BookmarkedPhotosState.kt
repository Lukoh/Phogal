package com.goforer.phogal.presentation.stateholder.uistate.home.setting.bookmark

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import com.goforer.phogal.data.model.remote.response.gallery.common.PhotoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Stable
class BookmarkedPhotosState(
    val uiState: StateFlow<MutableList<PhotoUiState>>,
    var enabledLoadState: MutableState<Boolean>,
)

@Composable
fun rememberBookmarkedPhotosState(
    uiState: StateFlow<MutableList<PhotoUiState>> = remember { MutableStateFlow(mutableListOf()) },
    enabledLoadState: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) }
): BookmarkedPhotosState = remember(uiState, enabledLoadState) {
    BookmarkedPhotosState(
        uiState = uiState,
        enabledLoadState = enabledLoadState
    )
}