package com.goforer.phogal.presentation.stateholder.uistate.home.common.photo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.goforer.phogal.data.datasource.network.response.Resource
import com.goforer.phogal.data.model.remote.response.gallery.common.PhotoUiState
import com.goforer.phogal.presentation.stateholder.uistate.BaseUiState
import com.goforer.phogal.presentation.stateholder.uistate.rememberBaseUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Stable
class PhotoContentState(
    val baseUiState: BaseUiState,
    val resourceState: StateFlow<Resource>,
    val idState: MutableState<String>,
    val visibleViewButtonState: MutableState<Boolean>,
    var enabledLoadState: MutableState<Boolean>,
    var enabledBookmarkState: MutableState<Boolean>,
    var enabledLikeState: MutableState<Boolean>,
    var visibleActionsState: MutableState<Boolean>
) {
    var photoUiState: PhotoUiState? = null
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberPhotoContentState(
    baseUiState: BaseUiState = rememberBaseUiState(),
    resourceState: StateFlow<Resource> = remember { MutableStateFlow(Resource()) },
    idState: MutableState<String> = rememberSaveable { mutableStateOf("") },
    visibleViewButtonState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    enabledLoadState: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) },
    enabledBookmarkState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    enabledLikeState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    visibleActionsState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
): PhotoContentState = remember(
    idState,
    resourceState,
    visibleViewButtonState,
    enabledLoadState
) {
    PhotoContentState(
        baseUiState = baseUiState,
        resourceState = resourceState,
        idState = idState,
        visibleViewButtonState = visibleViewButtonState,
        enabledLoadState = enabledLoadState,
        enabledBookmarkState = enabledBookmarkState,
        enabledLikeState = enabledLikeState,
        visibleActionsState = visibleActionsState
    )
}