package com.goforer.phogal.presentation.stateholder.uistate.home.gallery.photo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import com.goforer.phogal.data.model.remote.response.gallery.photo.Picture

@Stable
class PhotoContentState(
    val visibleViewPhotosButton: MutableState<Boolean>,
    var enabledLoadPhotos: MutableState<Boolean>,
    var enabledBookmark: MutableState<Boolean>,
    var visibleBookmark: MutableState<Boolean>
) {
    var picture: Picture? = null
}

@Composable
fun rememberPhotoContentState(
    visibleViewPhotosButton: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    enabledLoadPhotos: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) },
    enabledBookmark: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    visibleBookmark: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
): PhotoContentState = remember(
    visibleViewPhotosButton,
    enabledLoadPhotos
) {
    PhotoContentState(
        visibleViewPhotosButton = visibleViewPhotosButton,
        enabledLoadPhotos = enabledLoadPhotos,
        enabledBookmark = enabledBookmark,
        visibleBookmark = visibleBookmark
    )
}