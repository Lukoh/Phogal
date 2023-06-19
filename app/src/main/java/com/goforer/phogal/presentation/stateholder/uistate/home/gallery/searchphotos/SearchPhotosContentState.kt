package com.goforer.phogal.presentation.stateholder.uistate.home.gallery.searchphotos

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
    val searchWord: MutableState<String>,
    val enabledSearch: MutableState<Boolean>,
    val triggeredSearch: MutableState<Boolean>,
    val showPermissionBottomSheet: MutableState<Boolean>,
    val rationaleTextState: MutableState<String>,
    val photosUiState: StateFlow<Any>,
    val isRefreshing: StateFlow<Boolean>,
    var isScrolling: MutableState<Boolean>
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
    searchWord: MutableState<String> = rememberSaveable { mutableStateOf("") },
    enabledSearch: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    triggeredSearch: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    showPermissionBottomSheet: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    rationaleTextState: MutableState<String> = rememberSaveable { mutableStateOf("") },
    photosUiState: StateFlow<Any> = remember { MutableStateFlow(Any()) },
    isRefreshing: StateFlow<Boolean> = remember { MutableStateFlow(false) },
    isScrolling: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
): SearchPhotosContentState = remember(
    baseUiState,
    photosUiState,
    isRefreshing
) {
    SearchPhotosContentState(
        baseUiState = baseUiState,
        searchWord = searchWord,
        enabledSearch = enabledSearch,
        triggeredSearch = triggeredSearch,
        showPermissionBottomSheet = showPermissionBottomSheet,
        rationaleTextState = rationaleTextState,
        photosUiState = photosUiState,
        isRefreshing = isRefreshing,
        isScrolling = isScrolling
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
