package com.goforer.phogal.presentation.stateholder.uistate.home.photos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.CoroutineScope

@Stable
class ListSectionState(
    val scope: CoroutineScope,
    val refreshing: MutableState<Boolean>,
    var clickedState: MutableState<Boolean>,
)

@Composable
fun rememberListSectionState(
    scope: CoroutineScope = rememberCoroutineScope(),
    refreshing: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    clickedState: MutableState<Boolean> = remember { mutableStateOf(false) },
): ListSectionState = remember(
    clickedState,
) {
    ListSectionState(
        scope = scope,
        refreshing = refreshing,
        clickedState = clickedState
    )
}