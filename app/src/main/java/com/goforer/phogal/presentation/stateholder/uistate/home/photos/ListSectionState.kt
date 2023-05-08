package com.goforer.phogal.presentation.stateholder.uistate.home.photos

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.CoroutineScope

@Stable
class ListSectionState(
    val lazyListState: LazyListState,
    val visibleUpButtonState: State<Boolean>,
    val scope: CoroutineScope,
    var clickedState: MutableState<Boolean>,
)

@Composable
fun rememberListSectionState(
    lazyListState: LazyListState = rememberLazyListState(),
    visibleUpButtonState: State<Boolean> = remember { derivedStateOf { lazyListState.firstVisibleItemIndex > 0 } },
    scope: CoroutineScope = rememberCoroutineScope(),
    clickedState: MutableState<Boolean> = remember { mutableStateOf(false) },
): ListSectionState = remember(
    lazyListState,
    visibleUpButtonState,
    clickedState,
) {
    ListSectionState(
        lazyListState = lazyListState,
        visibleUpButtonState = visibleUpButtonState,
        scope = scope,
        clickedState = clickedState
    )
}