package com.goforer.phogal.presentation.stateholder.uistate.home.gallery

import android.Manifest
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
class SearchPhotosContentState(
    val baseUiState: BaseUiState,
    val wordState: MutableState<String>,
    val enabledState: MutableState<Boolean>,
    val triggeredState: MutableState<Boolean>,
    val permissionState: MutableState<Boolean>,
    val rationaleTextState: MutableState<String>,
    val photosUiState: StateFlow<Any>,
    val refreshingState: StateFlow<Boolean>,
    var scrollingState: MutableState<Boolean>,
    var removedWordState: MutableState<Boolean>
) {
    val permissions = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.CAMERA
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberSearchPhotosContentState(
    baseUiState: BaseUiState = rememberBaseUiState(),
    wordState: MutableState<String> = rememberSaveable { mutableStateOf("") },
    enabledState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    triggeredState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    permissionState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    rationaleTextState: MutableState<String> = rememberSaveable { mutableStateOf("") },
    photosUiState: StateFlow<Any> = remember { MutableStateFlow(Any()) },
    refreshingState: StateFlow<Boolean> = remember { MutableStateFlow(false) },
    scrollingState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    removedWordState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
): SearchPhotosContentState = remember(
    baseUiState,
    photosUiState,
    refreshingState
) {
    SearchPhotosContentState(
        baseUiState = baseUiState,
        wordState = wordState,
        enabledState = enabledState,
        triggeredState = triggeredState,
        permissionState = permissionState,
        rationaleTextState = rationaleTextState,
        photosUiState = photosUiState,
        refreshingState = refreshingState,
        scrollingState = scrollingState,
        removedWordState = removedWordState
    )
}

/*
@Stable
class PhotosContentState(
    var searchedKeywordState: MutableState<String>,
    val showTopButtonState: State<Boolean>,
    var clickedState: MutableState<Boolean>,
    val baseUiState: BaseUiState,
    val searchKeyword: MutableState<String>,
    val enabledSearch: MutableState<Boolean>,
    val showPermissionBottomSheet: MutableState<Boolean>,
    val rationaleTextState: MutableState<String>,
    val photosUiState: StateFlow<Any>,
    val isRefreshing: StateFlow<Boolean>
) {
    val permissions = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.CAMERA
    )
}

@Composable
fun rememberPhotosContentState(
    searchedKeywordState: MutableState<String> = rememberSaveable { mutableStateOf("") },
    showTopButtonState: State<Boolean> = remember { derivedStateOf { searchedKeywordState.value.isNotEmpty() } },
    clickedState: MutableState<Boolean> = remember { mutableStateOf(false) },
    baseUiState: BaseUiState,
    searchKeyword: MutableState<String> = rememberSaveable { mutableStateOf("") },
    enabledSearch: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    showPermissionBottomSheet: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    rationaleTextState: MutableState<String> = rememberSaveable { mutableStateOf("") },
    photosUiState: StateFlow<Any> = remember { MutableStateFlow(Any()) },
    isRefreshing: StateFlow<Boolean> = remember { MutableStateFlow(false) }
): PhotosContentState = remember(
    searchedKeywordState,
    showTopButtonState,
    clickedState,
    baseUiState,
    photosUiState,
    isRefreshing
) {
    PhotosContentState(
        searchedKeywordState = searchedKeywordState,
        showTopButtonState = showTopButtonState,
        clickedState = clickedState,
        baseUiState = baseUiState,
        searchKeyword = searchKeyword,
        enabledSearch = enabledSearch,
        showPermissionBottomSheet = showPermissionBottomSheet,
        rationaleTextState = rationaleTextState,
        photosUiState = photosUiState,
        isRefreshing = isRefreshing
    )
}

 */
