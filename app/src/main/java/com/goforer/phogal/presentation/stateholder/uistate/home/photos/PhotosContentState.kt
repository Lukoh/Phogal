package com.goforer.phogal.presentation.stateholder.uistate.home.photos

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import com.goforer.phogal.presentation.stateholder.uistate.BaseUiState

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
    rationaleTextState: MutableState<String> = rememberSaveable { mutableStateOf("") }
): PhotosContentState = remember(
    searchedKeywordState,
    showTopButtonState,
    clickedState,
    baseUiState
) {
    PhotosContentState(
        searchedKeywordState = searchedKeywordState,
        showTopButtonState = showTopButtonState,
        clickedState = clickedState,
        baseUiState = baseUiState,
        searchKeyword = searchKeyword,
        enabledSearch = enabledSearch,
        showPermissionBottomSheet = showPermissionBottomSheet,
        rationaleTextState = rationaleTextState
    )
}
