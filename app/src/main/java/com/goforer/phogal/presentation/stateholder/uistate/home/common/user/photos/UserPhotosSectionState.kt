package com.goforer.phogal.presentation.stateholder.uistate.home.common.user.photos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Stable
class UserPhotosSectionState(
    val uiState: StateFlow<Any>,
    val refreshingState: State<Boolean>,
    var clickedState: MutableState<Boolean>,
    var visibleUpButtonState: MutableState<Boolean>
)

@Composable
fun rememberUserPhotosSectionState(
    uiState: StateFlow<Any> = rememberSaveable { MutableStateFlow(Any()) },
    refreshingState: State<Boolean> = rememberSaveable { mutableStateOf(false) },
    clickedState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    visibleUpButtonState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
): UserPhotosSectionState = remember(
    clickedState,
) {
    UserPhotosSectionState(
        uiState = uiState,
        refreshingState = refreshingState,
        clickedState = clickedState,
        visibleUpButtonState = visibleUpButtonState
    )
}