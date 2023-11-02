package com.goforer.phogal.presentation.stateholder.uistate.home.common.user.photos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.goforer.phogal.presentation.stateholder.uistate.BaseUiState
import com.goforer.phogal.presentation.stateholder.uistate.rememberBaseUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Stable
class UserPhotosContentState(
    val baseUiState: BaseUiState,
    val nameState: State<String>,
    val photosUiState: StateFlow<Any>,
    val refreshingState: StateFlow<Boolean>,
    var enabledLoadState: MutableState<Boolean>
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberUserPhotosContentState(
    baseUiState: BaseUiState = rememberBaseUiState(),
    nameState: State<String> = rememberSaveable { mutableStateOf("") },
    photosUiState: StateFlow<Any> = remember { MutableStateFlow(Any()) },
    refreshingState: StateFlow<Boolean> = remember { MutableStateFlow(false) },
    enabledLoadState :  MutableState<Boolean> = rememberSaveable { mutableStateOf(true) }
): UserPhotosContentState = remember(
    baseUiState,
    nameState,
    photosUiState,
    refreshingState,
    enabledLoadState
) {
    UserPhotosContentState(
        baseUiState = baseUiState,
        nameState = nameState,
        photosUiState = photosUiState,
        refreshingState = refreshingState,
        enabledLoadState = enabledLoadState
    )
}