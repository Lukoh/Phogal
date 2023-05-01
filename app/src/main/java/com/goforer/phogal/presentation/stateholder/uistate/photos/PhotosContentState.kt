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
import com.goforer.phogal.data.model.state.ResourceState
import com.goforer.phogal.data.network.response.Resource
import com.goforer.phogal.data.network.response.Status
import com.goforer.phogal.presentation.stateholder.uistate.BaseUiState
import com.goforer.phogal.presentation.stateholder.uistate.EditableInputState
import com.goforer.phogal.presentation.stateholder.uistate.rememberEditableInputState
import com.goforer.phogal.presentation.stateholder.uistate.rememberResourceState
import kotlinx.coroutines.flow.StateFlow

@Stable
class PhotosContentState(
    val lazyListState: LazyListState,
    val editableInputState: EditableInputState,
    var searchedKeywordState: MutableState<String>,
    var enabledList: MutableState<Boolean>,
    val showButtonState: State<Boolean>,
    var clickedState: MutableState<Boolean>,
    val baseUiState: BaseUiState<Resource>,
    val resourceState: ResourceState<StateFlow<Resource>>,
    var selectedIndex: MutableState<Int>
) {
    lateinit var currentPhotosState: State<Resource>

    val resourceStateFlow: StateFlow<Resource>? = resourceState.resourceStateFlow
    val status: Status = resourceState.status
}

@Composable
fun rememberPhotosContentState(
    lazyListState: LazyListState = rememberLazyListState(),
    editableInputState: EditableInputState = rememberEditableInputState(hint = "Search"),
    searchedKeywordState: MutableState<String> = rememberSaveable { mutableStateOf("") },
    enabledList: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    showButtonState: State<Boolean> = remember { derivedStateOf { searchedKeywordState.value.isNotEmpty() } },
    clickedState: MutableState<Boolean> = remember { mutableStateOf(false) },
    baseUiState: BaseUiState<Resource>,
    resourceState: ResourceState<StateFlow<Resource>> = rememberResourceState(resourceStateFlow = baseUiState.resourceStateFlow),
    selectedIndex: MutableState<Int> = remember { mutableStateOf(-1) }
): PhotosContentState = remember(
    lazyListState,
    editableInputState,
    searchedKeywordState,
    enabledList,
    showButtonState,
    clickedState,
    baseUiState,
    resourceState,
    selectedIndex
) {
    PhotosContentState(
        lazyListState = lazyListState,
        editableInputState =  editableInputState,
        searchedKeywordState = searchedKeywordState,
        enabledList = enabledList,
        showButtonState = showButtonState,
        clickedState = clickedState,
        baseUiState = baseUiState,
        resourceState = resourceState,
        selectedIndex = selectedIndex
    )
}
