package com.goforer.phogal.presentation.stateholder.uistate.home.photos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import com.goforer.phogal.data.model.remote.response.photos.Document
import com.goforer.phogal.presentation.stateholder.uistate.BaseUiState

@Stable
class PhotosContentState(
    var searchedKeywordState: MutableState<String>,
    var enabledList: MutableState<Boolean>,
    val showTopButtonState: State<Boolean>,
    var clickedState: MutableState<Boolean>,
    val baseUiState: BaseUiState,
    var photos: MutableState<List<Document>>
)

@Composable
fun rememberPhotosContentState(
    searchedKeywordState: MutableState<String> = rememberSaveable { mutableStateOf("") },
    enabledList: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    showTopButtonState: State<Boolean> = remember { derivedStateOf { searchedKeywordState.value.isNotEmpty() } },
    clickedState: MutableState<Boolean> = remember { mutableStateOf(false) },
    baseUiState: BaseUiState,
    photos: MutableState<List<Document>> = rememberSaveable { mutableStateOf(emptyList()) }
): PhotosContentState = remember(
    searchedKeywordState,
    enabledList,
    showTopButtonState,
    clickedState,
    baseUiState,
    photos
) {
    PhotosContentState(
        searchedKeywordState = searchedKeywordState,
        enabledList = enabledList,
        showTopButtonState = showTopButtonState,
        clickedState = clickedState,
        baseUiState = baseUiState,
        photos = photos
    )
}
