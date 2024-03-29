package com.goforer.phogal.presentation.ui.compose.screen.home.popularphotos

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.goforer.base.designsystem.component.state.rememberLazyListState
import com.goforer.phogal.R
import com.goforer.phogal.data.model.local.error.ErrorThrowable
import com.goforer.phogal.data.repository.Repository
import com.goforer.phogal.presentation.analytics.TrackScreenViewEvent
import com.goforer.phogal.presentation.stateholder.business.home.common.bookmark.BookmarkViewModel
import com.goforer.phogal.presentation.stateholder.uistate.home.common.photo.rememberPhotoItemState
import com.goforer.phogal.presentation.stateholder.uistate.home.popularphotos.PopularPhotosSectionState
import com.goforer.phogal.presentation.stateholder.uistate.home.popularphotos.rememberPopularPhotosSectionState
import com.goforer.phogal.presentation.ui.compose.screen.home.gallery.LoadingPhotos
import com.goforer.phogal.presentation.ui.compose.screen.home.common.error.ErrorContent
import com.goforer.phogal.presentation.ui.compose.screen.home.common.photo.PhotoItem
import com.goforer.phogal.presentation.ui.compose.screen.home.common.photo.ShowUpButton
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray7
import com.google.gson.Gson
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun PopularPhotosSection(
    modifier: Modifier = Modifier,
    state: PopularPhotosSectionState = rememberPopularPhotosSectionState(),
    bookmarkViewModel: BookmarkViewModel = hiltViewModel(),
    onItemClicked: (id: String, index: Int) -> Unit,
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onShowSnackBar: (text: String) -> Unit,
    onOpenWebView: (firstName: String, url: String) -> Unit,
    onSuccess: (isSuccessful: Boolean) -> Unit
) {
    val photos = state.uiState.collectAsLazyPagingItems()
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
        photos.refresh()
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

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(0.2.dp))
            .pullRefresh(refreshState)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            state = lazyListState,
        ) {
            photos.loadState.apply {
                when {
                    refresh is LoadState.Loading -> {
                        item {
                            LoadingPhotos(
                                modifier = Modifier.padding(4.dp, 4.dp),
                                count = 3,
                                enableLoadIndicator = true
                            )
                        }
                    }
                    refresh is LoadState.NotLoading -> {
                        if (photos.itemCount == 0 ) {
                            state.visibleUpButtonState.value = false
                            onSuccess(false)
                            item {
                                Spacer(modifier = Modifier.height(320.dp))
                                Text(
                                    text = stringResource(id = R.string.no_picture),
                                    style = MaterialTheme.typography.titleMedium.copy(color = ColorSystemGray7),
                                    modifier = Modifier.align(Alignment.Center),
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        } else {
                            onSuccess(true)
                            items(
                                count = photos.itemCount,
                                key = photos.itemKey(
                                    key = { photo -> photo.id }
                                ),
                                contentType = photos.itemContentType()
                            ) { index ->
                                val padding = if (index == 0)
                                    2.dp
                                else
                                    0.5.dp
                                // After recreation, LazyPagingItems first return 0 items, then the cached items.
                                // This behavior/issue is resetting the LazyListState scroll position.
                                // Below is a workaround. More info: https://issuetracker.google.com/issues/177245496.
                                // If this bug will got fixed... then have to be removed below code
                                state.visibleUpButtonState.value = visibleUpButton(index)

                                PhotoItem(
                                    modifier = modifier
                                        .padding(top = padding)
                                        .animateItemPlacement(tween(durationMillis = 250)),
                                    state = rememberPhotoItemState(
                                        indexState = rememberSaveable { mutableIntStateOf(index) },
                                        photoState = rememberSaveable { mutableStateOf(photos[index]!!) },
                                        visibleViewButtonState = rememberSaveable { mutableStateOf(true) },
                                        bookmarkedState = rememberSaveable { mutableStateOf(bookmarkViewModel.isPhotoBookmarked(photos[index]!!.id)) }
                                    ),
                                    onItemClicked = onItemClicked,
                                    onViewPhotos = onViewPhotos,
                                    onShowSnackBar = onShowSnackBar,
                                    onOpenWebView = onOpenWebView
                                )
                                if (photos.itemCount < Repository.ITEM_COUNT && index == photos.itemCount - 1)
                                    Spacer(modifier = Modifier.height(26.dp))
                            }
                        }
                    }
                    refresh is LoadState.Error -> {
                        onSuccess(false)
                        item {
                            val error = (refresh as LoadState.Error).error.message
                            val errorThrowable = Gson().fromJson(error, ErrorThrowable::class.java)

                            AnimatedVisibility(
                                visible = true,
                                modifier = Modifier,
                                enter = scaleIn(transformOrigin = TransformOrigin(0f, 0f)) +
                                        fadeIn() + expandIn(expandFrom = Alignment.TopStart),
                                exit = scaleOut(transformOrigin = TransformOrigin(0f, 0f)) +
                                        fadeOut() + shrinkOut(shrinkTowards = Alignment.TopStart)
                            ) {
                                ErrorContent(
                                    modifier = modifier,
                                    title = if (errorThrowable.code !in 200..299)
                                        stringResource(id = R.string.error_dialog_network_title)
                                    else
                                        stringResource(id = R.string.error_dialog_title),
                                    message = if ((refresh as LoadState.Error).error.message == null)
                                        stringResource(id = R.string.error_dialog_content)
                                    else
                                        errorThrowable.message,
                                    onRetry = {
                                        photos.retry()
                                    }
                                )
                            }
                        }
                    }
                    append is LoadState.Loading -> {
                        Timber.d("Pagination Loading")
                    }
                    append is LoadState.Error -> {
                        Timber.d("Pagination broken Error")
                        onSuccess(false)
                        item {
                            val error = (append as LoadState.Error).error.message
                            val errorThrowable = Gson().fromJson(error, ErrorThrowable::class.java)

                            AnimatedVisibility(
                                visible = true,
                                modifier = Modifier,
                                enter = scaleIn(transformOrigin = TransformOrigin(0f, 0f)) +
                                        fadeIn() + expandIn(expandFrom = Alignment.TopStart),
                                exit = scaleOut(transformOrigin = TransformOrigin(0f, 0f)) +
                                        fadeOut() + shrinkOut(shrinkTowards = Alignment.TopStart)
                            ) {
                                ErrorContent(
                                    title = if (errorThrowable.code !in 200..299)
                                        stringResource(id = R.string.error_dialog_network_title)
                                    else
                                        stringResource(id = R.string.error_dialog_title),
                                    message = if ((append as LoadState.Error).error.message == null)
                                        stringResource(id = R.string.error_dialog_content)
                                    else
                                        errorThrowable.message,
                                    onRetry = {
                                        photos.retry()
                                    }
                                )
                            }
                        }
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

    if (photos.itemCount > 0 )
        TrackScreenViewEvent(screenName = "View_User_Photos")
}

private fun visibleUpButton(index: Int): Boolean {
    return when {
        index > 4 -> true
        index < 4-> false
        else -> true
    }
}