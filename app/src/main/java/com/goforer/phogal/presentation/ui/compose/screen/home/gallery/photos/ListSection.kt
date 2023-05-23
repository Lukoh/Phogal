@file:Suppress("UNCHECKED_CAST")

package com.goforer.phogal.presentation.ui.compose.screen.home.gallery.photos

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.goforer.base.extension.composable.rememberLazyListState
import com.goforer.phogal.R
import com.goforer.phogal.data.model.remote.response.gallery.photos.Photo
import com.goforer.phogal.presentation.stateholder.uistate.home.photos.ListSectionState
import com.goforer.phogal.presentation.stateholder.uistate.home.photos.rememberListSectionState
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListSection(
    modifier: Modifier = Modifier,
    state: ListSectionState = rememberListSectionState(),
    onItemClicked: (item: Photo, index: Int) -> Unit,
    onRefresh: () -> Unit
) {
    val photos = (state.photosUiState as StateFlow<PagingData<Photo>>).collectAsLazyPagingItems()
    // After recreation, LazyPagingItems first return 0 items, then the cached items.
    // This behavior/issue is resetting the LazyListState scroll position.
    // Below is a workaround. More info: https://issuetracker.google.com/issues/177245496.
    // If this bug will got fixed... then have to be removed below code
    val lazyListState = photos.rememberLazyListState()
    // After recreation, LazyPagingItems first return 0 items, then the cached items.
    // This behavior/issue is resetting the LazyListState scroll position.
    // Below is a workaround. More info: https://issuetracker.google.com/issues/177245496.
    // If this bug will got fixed... then have to be unblocked below code
    //val lazyListState = rememberLazyListState()
    val refreshState = rememberPullRefreshState(state.refreshingState.value, onRefresh = {
        onRefresh()
    })
    // After recreation, LazyPagingItems first return 0 items, then the cached items.
    // This behavior/issue is resetting the LazyListState scroll position.
    // Below is a workaround. More info: https://issuetracker.google.com/issues/177245496.
    // If this bug will got fixed... then have to be unblocked below code
    /*
    val visibleUpButtonState by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 0
        }
    }

     */

    BoxWithConstraints(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .pullRefresh(refreshState)
    ) {
        LazyColumn(
            modifier = Modifier.animateContentSize(),
            state = lazyListState
        ) {
            if (!state.refreshingState.value) {
                state.openedErrorDialogState.value = when(photos.loadState.refresh) {
                    is LoadState.Error -> {
                        true
                    }

                    is LoadState.NotLoading -> {
                        items(
                            count = photos.itemCount,
                            key = photos.itemKey(),
                            contentType = photos.itemContentType()
                        ) { index ->
                            // After recreation, LazyPagingItems first return 0 items, then the cached items.
                            // This behavior/issue is resetting the LazyListState scroll position.
                            // Below is a workaround. More info: https://issuetracker.google.com/issues/177245496.
                            // If this bug will got fixed... then have to be removed below code
                            state.visibleUpButtonState.value = visibleUpButton(index)
                            PhotoItem(
                                modifier = modifier,
                                index = index,
                                photo = photos[index]!!,
                                onItemClicked = onItemClicked
                            )
                        }

                        false
                    }

                    is LoadState.Loading -> {
                        item {
                            LoadingPhotos(
                                modifier = Modifier.padding(4.dp, 4.dp),
                                count = 3,
                                enableLoadIndicator = true
                            )
                        }

                        false
                    }

                    else -> {
                        false
                    }
                }

                state.openedErrorDialogState.value = when(photos.loadState.append) {
                    is LoadState.Error -> {
                        true
                    }

                    is LoadState.Loading -> {
                        Timber.d("Pagination Loading")

                        false
                    }

                    else -> {
                        false
                    }
                }
            }
        }

        PullRefreshIndicator(state.refreshingState.value, refreshState, Modifier.align(Alignment.TopCenter))
        if (!lazyListState.isScrollInProgress) {
            ShowUpButton(
                modifier = Modifier.align(Alignment.BottomEnd),
                visible = state.visibleUpButtonState.value,
                onClick = {
                    state.clickedState.value = true
                }
            )
        }

        LaunchedEffect(lazyListState, true, state.clickedState.value) {
            if (state.clickedState.value) {
                lazyListState.animateScrollToItem (0)
                state.visibleUpButtonState.value = false
            }

            state.clickedState.value = false
        }
    }

    if (state.openedErrorDialogState.value) {
        ShowAlertDialog(
            onDismissRequest = {
                state.openedErrorDialogState.value = false
            },
            onConfirmClick = {
                state.openedErrorDialogState.value = false
            }
        )
    }
}

@Composable
fun ShowUpButton(modifier: Modifier, visible: Boolean, onClick: () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier
    ) {
        FloatingActionButton(
            modifier = modifier
                .navigationBarsPadding()
                .padding(bottom = 4.dp, end = 8.dp),
            backgroundColor = MaterialTheme.colorScheme.primary,
            onClick = onClick
        ) {
            Text("Up!")
        }
    }
}

@Composable
fun ShowAlertDialog(
    error: Error? = null,
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            if (error?.errorCode in 200..299)
                Text(text = stringResource(id = R.string.error_dialog_title))
            else
                Text(text = stringResource(id = R.string.error_dialog_network_title))
        },
        text = {
            Text("stringResource(id = R.string.error_dialog_content)${"\\n"}${error?.errorCause ?: ""}${"\\n"}${error?.errorMessage ?: ""}")
        },
        confirmButton = {
            Button(
                onClick = onConfirmClick
            ) {
                Text(stringResource(id = R.string.confirm))
            }
        },
    )
}

private fun visibleUpButton(index: Int): Boolean {
    return when {
        index > 2 -> true
        index < 2-> false
        else -> true
    }
}

data class Error(
    val errorCode: Int? = null,
    val errorCause: String? = null,
    val errorMessage: String
)