package com.goforer.phogal.presentation.stateholder.uistate.home.common.photo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo.PictureUiState
import com.goforer.phogal.presentation.stateholder.uistate.BaseUiState
import com.goforer.phogal.presentation.stateholder.uistate.rememberBaseUiState

@Stable
class PhotoContentState(
    val baseUiState: BaseUiState,
    val idState: MutableState<String>,
    val visibleViewButtonState: MutableState<Boolean>,
    var enabledLoadState: MutableState<Boolean>,
    var enabledBookmarkState: MutableState<Boolean>,
    var enabledLikeState: MutableState<Boolean>,
    var visibleActionsState: MutableState<Boolean>
) {
    var picture: PictureUiState? = null
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberPhotoContentState(
    baseUiState: BaseUiState = rememberBaseUiState(),
    idState: MutableState<String> = rememberSaveable { mutableStateOf("") },
    visibleViewButtonState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    enabledLoadState: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) },
    enabledBookmarkState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    enabledLikeState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    visibleActionsState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
): PhotoContentState = remember(
    idState,
    visibleViewButtonState,
    enabledLoadState
) {
    PhotoContentState(
        baseUiState = baseUiState,
        idState = idState,
        visibleViewButtonState = visibleViewButtonState,
        enabledLoadState = enabledLoadState,
        enabledBookmarkState = enabledBookmarkState,
        enabledLikeState = enabledLikeState,
        visibleActionsState = visibleActionsState
    )
}