package com.goforer.phogal.presentation.stateholder.uistate.home.common.photo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo.Picture
import com.goforer.phogal.presentation.stateholder.uistate.BaseUiState
import com.goforer.phogal.presentation.stateholder.uistate.rememberBaseUiState

@Stable
class PhotoContentState(
    val baseUiState: BaseUiState,
    val id: MutableState<String>,
    val visibleViewPhotosButton: MutableState<Boolean>,
    var enabledLoadPhotos: MutableState<Boolean>,
    var enabledBookmark: MutableState<Boolean>,
    var enabledLike: MutableState<Boolean>,
    var visibleActions: MutableState<Boolean>
) {
    var picture: Picture? = null
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberPhotoContentState(
    baseUiState: BaseUiState = rememberBaseUiState(),
    id: MutableState<String> = rememberSaveable { mutableStateOf("") },
    visibleViewPhotosButton: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    enabledLoadPhotos: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) },
    enabledBookmark: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    enabledLike: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    visibleActions: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
): PhotoContentState = remember(
    id,
    visibleViewPhotosButton,
    enabledLoadPhotos
) {
    PhotoContentState(
        baseUiState = baseUiState,
        id = id,
        visibleViewPhotosButton = visibleViewPhotosButton,
        enabledLoadPhotos = enabledLoadPhotos,
        enabledBookmark = enabledBookmark,
        enabledLike = enabledLike,
        visibleActions = visibleActions
    )
}