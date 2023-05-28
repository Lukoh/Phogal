package com.goforer.phogal.presentation.stateholder.uistate.home.gallery.userphotos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.goforer.phogal.presentation.stateholder.uistate.BaseUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Stable
class UserPhotosContentState(
    val baseUiState: BaseUiState,
    val photosUiState: StateFlow<Any>,
    val isRefreshing: StateFlow<Boolean>
)

@Composable
fun rememberUserPhotosContentState(
    baseUiState: BaseUiState,
    photosUiState: StateFlow<Any> = remember { MutableStateFlow(Any()) },
    isRefreshing: StateFlow<Boolean> = remember { MutableStateFlow(false) }
): UserPhotosContentState = remember(
    baseUiState,
    photosUiState,
    isRefreshing
) {
    UserPhotosContentState(
        baseUiState = baseUiState,
        photosUiState = photosUiState,
        isRefreshing = isRefreshing
    )
}