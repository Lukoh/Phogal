package com.goforer.phogal.presentation.stateholder.uistate.home.common.photo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable

@Stable
class PhotoItemState(
    val index:  MutableState<Int>,
    val photo: State<Any>,
    val visibleViewPhotosButton: MutableState<Boolean>,
    val isClicked: MutableState<Boolean>,
    val bookmarked: MutableState<Boolean>
)

@Composable
fun rememberPhotoItemState(
    index: MutableState<Int> = rememberSaveable { mutableIntStateOf(0) },
    photo: State<Any> = remember { mutableStateOf(Any()) },
    visibleViewPhotosButton: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    isClicked: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    bookmarked: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
): PhotoItemState = remember(index, photo, visibleViewPhotosButton, isClicked) {
    PhotoItemState(
        index = index,
        visibleViewPhotosButton = visibleViewPhotosButton,
        photo = photo,
        isClicked = isClicked,
        bookmarked = bookmarked
    )
}