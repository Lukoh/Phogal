package com.goforer.phogal.presentation.ui.compose.screen.home.photo

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.goforer.phogal.R
import com.goforer.phogal.data.model.remote.response.photos.Document
import com.goforer.phogal.presentation.stateholder.uistate.home.photos.ListSectionState
import com.goforer.phogal.presentation.stateholder.uistate.home.photos.rememberListSectionState
import com.goforer.phogal.presentation.ui.theme.PhogalTheme
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListSection(
    modifier: Modifier = Modifier,
    state: ListSectionState = rememberListSectionState(),
    photos: LazyPagingItems<Document>,
    onItemClicked: (item: Document, index: Int) -> Unit,
    onRefresh: () -> Unit
) {
    var openedErrorDialog by rememberSaveable { mutableStateOf(false) }
    val refreshState = rememberPullRefreshState(state.refreshing.value, onRefresh = {
        state.scope.launch {
            state.refreshing.value = true
            onRefresh()
        }
    })

    BoxWithConstraints(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .pullRefresh(refreshState)
    ) {
        LazyColumn(
            modifier = Modifier
                .animateContentSize(),
            state = state.lazyListState,
        ) {
            if (!state.refreshing.value) {
                itemsIndexed(photos.itemSnapshotList.items , key = { _, item -> item.url!! }, itemContent = { index, item ->
                    PhotoItem(
                        modifier = modifier,
                        index = index,
                        document = item,
                        onItemClicked = onItemClicked
                    )
                })

                openedErrorDialog = when(photos.loadState.refresh) {
                    is LoadState.Error -> {
                        true
                    }

                    is LoadState.Loading -> {
                        item {
                            LoadingPhotos(Modifier.padding(4.dp, 8.dp),3)
                            //LoadingIndicator(modifier = Modifier, DarkGreen10)
                        }

                        false
                    }

                    else -> {
                        false
                    }
                }

                openedErrorDialog = when(photos.loadState.append) {
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

        PullRefreshIndicator(state.refreshing.value, refreshState, Modifier.align(Alignment.TopCenter))
        AnimatedVisibility(
            visible = state.visibleUpButtonState.value,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(bottom = 4.dp, end = 8.dp),
                backgroundColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    state.clickedState.value = true
                    // Can scrolling to the first item using below code....
                    /*
                    state.scope.launch {
                        state.lazyListState.scrollToItem(0)
                    }

                     */
                }
            ) {
                Text("Up!")
            }
        }

        LaunchedEffect(state.lazyListState, state.visibleUpButtonState.value, state.clickedState.value) {
            if (state.visibleUpButtonState.value && state.clickedState.value)
                state.lazyListState.scrollToItem (0)

            state.clickedState.value = false
        }
    }

    if (openedErrorDialog) {
        AlertDialog(
            onDismissRequest = {
                openedErrorDialog = false
            },
            title = {
                Text(text = stringResource(id = R.string.error_dialog_title))
            },
            text = {
                Text(stringResource(id = R.string.error_dialog_content))
            },
            confirmButton = {
                Button(
                    onClick = {
                        openedErrorDialog = false
                    }) {
                    Text(stringResource(id = R.string.confirm))
                }
            },
        )
    }
}

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
                            document = item,
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