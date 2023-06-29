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
    val popularPhotosUiState: StateFlow<Any>,
    val isRefreshing: StateFlow<Boolean>,
    var enabledLoadPhotos: MutableState<Boolean>,
    var visibleActions: MutableState<Boolean>
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberPopularPhotosContentState(
    baseUiState: BaseUiState = rememberBaseUiState(),
    popularPhotosUiState: StateFlow<Any> = remember { MutableStateFlow(Any()) },
    isRefreshing: StateFlow<Boolean> = remember { MutableStateFlow(false) },
    enabledLoadPhotos: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) },
    visibleActions: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) }
): PopularPhotosContentState = remember(
    baseUiState,
    popularPhotosUiState,
    isRefreshing
) {
    PopularPhotosContentState(
        baseUiState = baseUiState,
        popularPhotosUiState = popularPhotosUiState,
        isRefreshing = isRefreshing,
        enabledLoadPhotos = enabledLoadPhotos,
        visibleActions = visibleActions
    )
}
