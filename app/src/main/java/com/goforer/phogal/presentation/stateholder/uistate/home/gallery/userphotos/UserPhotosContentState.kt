package com.goforer.phogal.presentation.stateholder.uistate.home.gallery.userphotos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.goforer.phogal.presentation.stateholder.uistate.BaseUiState
import com.goforer.phogal.presentation.stateholder.uistate.rememberBaseUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Stable
class UserPhotosContentState(
    val baseUiState: BaseUiState,
    val photosUiState: StateFlow<Any>,
    val isRefreshing: StateFlow<Boolean>,
    var enabledLoadPhotos: MutableState<Boolean>
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberUserPhotosContentState(
    baseUiState: BaseUiState = rememberBaseUiState(),
    photosUiState: StateFlow<Any> = remember { MutableStateFlow(Any()) },
    isRefreshing: StateFlow<Boolean> = remember { MutableStateFlow(false) },
    enabledLoadPhotos :  MutableState<Boolean> = rememberSaveable { mutableStateOf(true) }
): UserPhotosContentState = remember(
    baseUiState,
    photosUiState,
    isRefreshing,
    enabledLoadPhotos
) {
    UserPhotosContentState(
        baseUiState = baseUiState,
        photosUiState = photosUiState,
        isRefreshing = isRefreshing,
        enabledLoadPhotos = enabledLoadPhotos
    )
}