package com.goforer.phogal.presentation.stateholder.uistate.home.photos

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
class ListSectionState(
    val photosUiState: StateFlow<Any>,
    val scope: CoroutineScope,
    val refreshingState: State<Boolean>,
    var clickedState: MutableState<Boolean>,
    var visibleUpButtonState: MutableState<Boolean>
)

@Composable
fun rememberListSectionState(
    scope: CoroutineScope = rememberCoroutineScope(),
    photosUiState: StateFlow<Any> = rememberSaveable { MutableStateFlow(Any()) },
    refreshingState: State<Boolean> = rememberSaveable { mutableStateOf(false) },
    clickedState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    visibleUpButtonState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
): ListSectionState = remember(
    clickedState,
) {
    ListSectionState(
        scope = scope,
        photosUiState = photosUiState,
        refreshingState = refreshingState,
        clickedState = clickedState,
        visibleUpButtonState = visibleUpButtonState
    )
}