package com.goforer.phogal.presentation.stateholder.uistate.photos

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import com.goforer.phogal.data.model.response.Document
import com.goforer.phogal.data.model.state.ResourceState
import com.goforer.phogal.data.network.response.Resource
import com.goforer.phogal.data.network.response.Status
import com.goforer.phogal.presentation.stateholder.uistate.BaseUiState
import com.goforer.phogal.presentation.stateholder.uistate.rememberResourceState
import kotlinx.coroutines.flow.StateFlow

@Stable
class PhotosContentState(
    val lazyListState: LazyListState,
    var searchedKeywordState: MutableState<String>,
    var enabledList: MutableState<Boolean>,
    val showTopButtonState: State<Boolean>,
    var clickedState: MutableState<Boolean>,
    val baseUiState: BaseUiState<Resource>,
    val resourceState: ResourceState<StateFlow<Resource>>,
    var photos: MutableState<List<Document>>
) {
    val status: Status = resourceState.status
}

@Composable
fun rememberPhotosContentState(
    lazyListState: LazyListState = rememberLazyListState(),
    searchedKeywordState: MutableState<String> = rememberSaveable { mutableStateOf("") },
    enabledList: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    showTopButtonState: State<Boolean> = remember { derivedStateOf { searchedKeywordState.value.isNotEmpty() } },
    clickedState: MutableState<Boolean> = remember { mutableStateOf(false) },
    baseUiState: BaseUiState<Resource>,
    resourceState: ResourceState<StateFlow<Resource>> = rememberResourceState(resourceStateFlow = baseUiState.resourceStateFlow),
    photos: MutableState<List<Document>> = rememberSaveable { mutableStateOf(emptyList()) }
): PhotosContentState = remember(
    lazyListState,
    searchedKeywordState,
    enabledList,
    showTopButtonState,
    clickedState,
    baseUiState,
    resourceState,
    photos
) {
    PhotosContentState(
        lazyListState = lazyListState,
        searchedKeywordState = searchedKeywordState,
        enabledList = enabledList,
        showTopButtonState = showTopButtonState,
        clickedState = clickedState,
        baseUiState = baseUiState,
        resourceState = resourceState,
        photos = photos
    )
}
