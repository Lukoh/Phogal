package com.goforer.phogal.presentation.stateholder.uistate.home.popularphotos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Stable
class PopularPhotosSectionState(
    val popularPhotosUiState: StateFlow<Any>,
    val scope: CoroutineScope,
    val refreshingState: State<Boolean>,
    var clickedState: MutableState<Boolean>,
    var visibleUpButtonState: MutableState<Boolean>
)

@Composable
fun rememberPopularPhotosSectionState(
    scope: CoroutineScope = rememberCoroutineScope(),
    popularPhotosUiState: StateFlow<Any> = rememberSaveable { MutableStateFlow(Any()) },
    refreshingState: State<Boolean> = rememberSaveable { mutableStateOf(false) },
    clickedState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    visibleUpButtonState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
): PopularPhotosSectionState = remember(
    clickedState,
) {
    PopularPhotosSectionState(
        scope = scope,
        popularPhotosUiState = popularPhotosUiState,
        refreshingState = refreshingState,
        clickedState = clickedState,
        visibleUpButtonState = visibleUpButtonState
    )
}