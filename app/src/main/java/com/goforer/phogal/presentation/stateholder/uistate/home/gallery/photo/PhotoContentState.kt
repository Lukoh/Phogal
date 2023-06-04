package com.goforer.phogal.presentation.stateholder.uistate.home.gallery.photo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable

@Stable
class PhotoContentState(
    val visibleViewPhotosButton: MutableState<Boolean>,
    var enabledLoadPhotos: MutableState<Boolean>
)

@Composable
fun rememberPhotoContentState(
    visibleViewPhotosButton: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    enabledLoadPhotos :  MutableState<Boolean> = rememberSaveable { mutableStateOf(true) }
): PhotoContentState = remember(
    visibleViewPhotosButton,
    enabledLoadPhotos
) {
    PhotoContentState(
        visibleViewPhotosButton = visibleViewPhotosButton,
        enabledLoadPhotos = enabledLoadPhotos
    )
}