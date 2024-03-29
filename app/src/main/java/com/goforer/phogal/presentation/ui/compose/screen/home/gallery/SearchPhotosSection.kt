package com.goforer.phogal.presentation.ui.compose.screen.home.gallery

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
import com.goforer.base.designsystem.component.state.rememberCurrentScrollOffset
import com.goforer.base.designsystem.component.state.rememberLazyListState
import com.goforer.phogal.R
import com.goforer.phogal.data.model.local.error.ErrorThrowable
import com.goforer.phogal.data.repository.Repository
import com.goforer.phogal.presentation.analytics.TrackScreenViewEvent
import com.goforer.phogal.presentation.stateholder.business.home.common.bookmark.BookmarkViewModel
import com.goforer.phogal.presentation.stateholder.uistate.home.common.photo.rememberPhotoItemState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.SearchPhotosSectionState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.rememberSearchPhotosSectionState
import com.goforer.phogal.presentation.ui.compose.screen.home.common.error.ErrorContent
import com.goforer.phogal.presentation.ui.compose.screen.home.common.photo.PhotoItem
import com.goforer.phogal.presentation.ui.compose.screen.home.common.photo.ShowUpButton
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray7
import com.google.gson.Gson
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun SearchPhotosSection(
    modifier: Modifier = Modifier,
    bookmarkViewModel: BookmarkViewModel = hiltViewModel(),
    state: SearchPhotosSectionState = rememberSearchPhotosSectionState(),
    onItemClicked: (id: String, index: Int) -> Unit,
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onShowSnackBar: (text: String) -> Unit,
    onLoadSuccess: (isSuccessful: Boolean) -> Unit,
    onScroll: (isScrolling: Boolean) -> Unit,
    onOpenWebView: (firstName: String, url: String) -> Unit
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
            .pullRefresh(refreshState)
            .clip(RoundedCornerShape(0.2.dp))
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            state = lazyListState
        ) {
            photos.loadState.apply {
                when {
                    refresh is LoadState.Loading -> {
                        state.clickedState.value = true
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
                            onLoadSuccess(false)
                            state.visibleUpButtonState.value = false
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
                            onLoadSuccess(true)
                            items(
                                count = photos.itemCount,
                                key = photos.itemKey(
                                    key = { photo -> photo.id }
                                ),
                                contentType = photos.itemContentType()
                            ) { index ->
                                // After recreation, LazyPagingItems first return 0 items, then the cached items.
                                // This behavior/issue is resetting the LazyListState scroll position.
                                // Below is a workaround. More info: https://issuetracker.google.com/issues/177245496.
                                // If this bug will got fixed... then have to be removed below code
                                state.visibleUpButtonState.value = visibleUpButton(index)
                                PhotoItem(
                                    modifier = modifier.animateItemPlacement(
                                        tween(durationMillis = 250)
                                    ),
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

                                TrackScreenViewEvent(screenName = "SearchPhotosSection")
                            }
                        }
                    }
                    refresh is LoadState.Error -> {
                        onLoadSuccess(false)
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
                        onLoadSuccess(false)
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
            onScroll(false)
        } else {
            val offset = lazyListState.rememberCurrentScrollOffset()

            if (offset.value > 35)
                onScroll(true)
        }
    }

    LaunchedEffect(lazyListState, state.clickedState.value) {
        if (state.clickedState.value)
            lazyListState.animateScrollToItem (0)

        state.clickedState.value = false
    }
}

private fun visibleUpButton(index: Int): Boolean {
    return when {
        index > 4 -> true
        index < 4-> false
        else -> true
    }
}

/*
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode",
    showSystemUi = true
)
@Composable
fun ListSectionPreview(modifier: Modifier = Modifier) {
    PhogalTheme {
        val lazyListState: LazyListState = rememberLazyListState()
        val photos = mutableListOf(
            Document("_SBS","news", "2017-01-21T15:59:30.000+09:00", "한국경제TV","http://v.media.daum.net/v/20170621155930002",457, "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg", 185,"https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F", "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp","AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’", "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634"),
            Document("_JTBC","news", "2017-02-21T15:59:30.000+09:00", "한국경제TV","http://v.media.daum.net/v/20170621155930002",457, "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg", 185,"https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F", "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp","AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’", "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634"),
            Document("_KBS","news", "2017-03-21T15:59:30.000+09:00", "한국경제TV","http://v.media.daum.net/v/20170621155930002",457, "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg", 185,"https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F", "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp","AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’", "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634"),
            Document("_SBS","news", "2017-04-21T15:59:30.000+09:00", "한국경제TV","http://v.media.daum.net/v/20170621155930002",457,"http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg", 185,"https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F", "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp","AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’", "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634"),
            Document("_SBS","news", "2017-05-21T15:59:30.000+09:00", "한국경제TV","http://v.media.daum.net/v/20170621155930002",457,"http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg", 185,"https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F", "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp","AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’", "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634"),
            Document("_SBS","news", "2017-06-21T15:59:30.000+09:00", "한국경제TV","http://v.media.daum.net/v/20170621155930002",457,"http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg", 185,"https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F", "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp","AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’", "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634"),
            Document("_SBS","news", "2017-07-21T15:59:30.000+09:00", "한국경제TV","http://v.media.daum.net/v/20170621155930002",457, "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg", 185,"https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F", "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp","AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’", "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634"),
            Document("_SBS","news", "2017-08-21T15:59:30.000+09:00", "한국경제TV","http://v.media.daum.net/v/20170621155930002",457, "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg", 185,"https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F", "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp","AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’", "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634"),
            Document("_SBS","news", "2017-09-21T15:59:30.000+09:00", "한국경제TV","http://v.media.daum.net/v/20170621155930002",457, "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg", 185,"https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F", "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp","AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’", "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634"),
            Document("_SBS","news", "2017-10-21T15:59:30.000+09:00", "한국경제TV","http://v.media.daum.net/v/20170621155930002",457, "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg", 185,"https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F", "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp","AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’", "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634"),
            Document("_SBS","news", "2017-11-21T15:59:30.000+09:00", "한국경제TV","http://v.media.daum.net/v/20170621155930002",457, "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg", 185,"https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F", "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp","AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’", "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634")
        )

        BoxWithConstraints(modifier = modifier) {
            LazyColumn(
                modifier = Modifier,
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                itemsIndexed(
                    photos,
                    key = { _, item -> item.datetime },
                    itemContent = { index, item ->
                        PhotoItem(
                            modifier = modifier,
                            index = index,
                            photo = item,
                            onItemClicked = { _, _ -> }
                        )
                    })
            }

            AnimatedVisibility(
                visible = true,
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .navigationBarsPadding()
                        .padding(bottom = 4.dp, end = 8.dp),
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    onClick = {
                    }
                ) {
                    Text("Up!")
                }
            }
        }
    }
}

 */