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
import com.goforer.phogal.data.network.response.Resource
import com.goforer.phogal.data.network.response.Status
import com.goforer.phogal.presentation.stateholder.uistate.BaseUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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
    val photosUiState: StateFlow<Resource>,
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
    photosUiState: StateFlow<Resource> = remember { MutableStateFlow(Resource().loading(Status.LOADING)) },
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
