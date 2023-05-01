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
import androidx.compose.runtime.snapshotFlow
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.goforer.phogal.data.model.response.Document
import com.goforer.phogal.data.network.response.Resource

@Stable
class ListSectionState(
    val lazyListState: LazyListState,
    val visibleUpButtonState: State<Boolean>,
    var clickedState: MutableState<Boolean>,
    val photos: List<Document>,
    val likedState: MutableState<Boolean>,
)

@Composable
fun rememberListSectionState(
    lazyListState: LazyListState = rememberLazyListState(),
    visibleUpButtonState: State<Boolean> = remember { derivedStateOf { lazyListState.firstVisibleItemIndex > 0 } },
    clickedState: MutableState<Boolean> = remember { mutableStateOf(false) },
    photos: List<Document> = remember { listOf() },
    likedState: MutableState<Boolean> = remember { mutableStateOf(false) }
): ListSectionState = remember(
    lazyListState,
    visibleUpButtonState,
    clickedState,
    photos,
    likedState
) {
    ListSectionState(
        lazyListState = lazyListState,
        visibleUpButtonState = visibleUpButtonState,
        clickedState = clickedState,
        photos = photos,
        likedState = likedState
    )
}