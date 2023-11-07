package com.goforer.phogal.presentation.stateholder.uistate.home.popularphotos

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
class PopularPhotosContentState(
    val baseUiState: BaseUiState,
    val uiState: StateFlow<Any>,
    val refreshingState: StateFlow<Boolean>,
    var enabledLoadState: MutableState<Boolean>,
    var visibleActionsState: MutableState<Boolean>
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberPopularPhotosContentState(
    baseUiState: BaseUiState = rememberBaseUiState(),
    uiState: StateFlow<Any> = remember { MutableStateFlow(Any()) },
    refreshingState: StateFlow<Boolean> = remember { MutableStateFlow(false) },
    enabledLoadState: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) },
    visibleActionsState: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) }
): PopularPhotosContentState = remember(
    baseUiState,
    uiState,
    refreshingState
) {
    PopularPhotosContentState(
        baseUiState = baseUiState,
        uiState = uiState,
        refreshingState = refreshingState,
        enabledLoadState = enabledLoadState,
        visibleActionsState = visibleActionsState
    )
}
