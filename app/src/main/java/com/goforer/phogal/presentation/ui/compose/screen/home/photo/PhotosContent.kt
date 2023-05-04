package com.goforer.phogal.presentation.ui.compose.screen.home.photo

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.goforer.phogal.R
import com.goforer.phogal.data.model.response.Document
import com.goforer.phogal.data.network.response.Status
import com.goforer.phogal.data.repository.Repository.Companion.replyCount
import com.goforer.phogal.presentation.stateholder.uistate.home.photos.PhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.photos.rememberListSectionState
import com.goforer.phogal.presentation.ui.theme.PhogalTheme
import timber.log.Timber

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun PhotosContent(
    modifier: Modifier = Modifier,
    state: PhotosContentState,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    onSearched: (keyword: String) -> Unit,
    onItemClicked: (item: Document, index: Int) -> Unit
) {
    replyCount = 2
    /*
    LaunchedEffect(state.selectedIndex.value, state.baseUiState.lifecycle) {
        if (state.selectedIndex.value != -1)
            Toast.makeText(state.baseUiState.context, "Show the detailed profile!", Toast.LENGTH_SHORT).show()
    }
    */

    when(state.status) {
        Status.SUCCESS -> {
            state.resourceState.resourceStateFlow?.let {
                val currentPhotosState = it.collectAsStateWithLifecycle()

                state.enabledList.value = true

                @Suppress("UNCHECKED_CAST")
                state.photos.value = snapshotFlow {
                    (currentPhotosState.value.data as State<PagingData<Document>>).value
                }.collectAsLazyPagingItems().itemSnapshotList.items
            }
        }
        Status.LOADING -> {
            // To Do : run the loading animation or shimmer
            state.enabledList.value = false
        }
        Status.ERROR -> {
            // To Do : handle the error
            state.enabledList.value = false
            state.resourceState.resourceStateFlow?.let {
                Timber.d("Error Code - %d & Error Message - %s", it.value.errorCode, it.value.message)
            }
        }
    }

    /*
    if (!photosSectionState.editableInputState.isHint)
        onSearched(photosSectionState.editableInputState.textState, false)

     */

    BoxWithConstraints(modifier = modifier) {
        Column(
            modifier = modifier
                .padding(
                    0.dp,
                    contentPadding.calculateTopPadding(),
                    0.dp,
                    0.dp
                )
        ) {
            SearchSection(
                modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp),
                onSearched = { keyword ->
                    state.baseUiState.keyboardController?.hide()
                    onSearched(keyword)
                }
            )
            if (state.enabledList.value) {
                ListSection(
                    modifier = Modifier.weight(1f),
                    state = rememberListSectionState(photos = state.photos.value),
                    onItemClicked = { item, index ->
                        onItemClicked(item, index)
                    }
                )
            }
        }

        AnimatedVisibility(
            enter = expandVertically(),
            exit = shrinkVertically(),
            visible = state.showTopButtonState.value,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            val background by transition.animateColor(label = "FloatingAnimation") { state ->
                if (state == EnterExitState.Visible) MaterialTheme.colorScheme.primary else Color.Gray
            }

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(bottom = 4.dp, end = 8.dp),
                backgroundColor = background,
                onClick = {
                    state.clickedState.value = true
                }
            ) {
                Text("Back!")
            }
        }

        if (state.showTopButtonState.value && state.clickedState.value) {
            LaunchedEffect(state.lazyListState, state.showTopButtonState.value, state.clickedState.value) {
                if (state.showTopButtonState.value && state.clickedState.value)
                    state.lazyListState.scrollToItem (0)

                state.clickedState.value = false
            }
        }
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
fun PhotosContentPreview(modifier: Modifier = Modifier) {
    PhogalTheme {
        BoxWithConstraints(modifier = modifier) {
            Column(
                modifier = modifier
                    .padding(
                        0.dp,
                        56.dp,
                        0.dp,
                        0.dp
                    )
            ) {
                val photos = listOf(
                    Document("_SBS","news", "2017-06-21T15:59:30.000+09:00", "한국경제TV","http://v.media.daum.net/v/20170621155930002",457, "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg", 185,"https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F", "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp","AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’", "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634"),
                    Document("_JTBC","news", "2017-07-21T15:59:30.000+09:00", "한국경제TV","http://v.media.daum.net/v/20170621155930002",457, "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg", 185,"https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F", "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp","AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’", "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634"),
                    Document("_KBS","news", "2017-08-21T15:59:30.000+09:00", "한국경제TV","http://v.media.daum.net/v/20170621155930002",457, "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg", 185,"https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F", "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp","AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’", "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634"),
                    Document("_SBS","news", "2017-09-21T15:59:30.000+09:00", "한국경제TV","http://v.media.daum.net/v/20170621155930002",457,"http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg", 185,"https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F", "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp","AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’", "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634"),
                    Document("_SBS","news", "2017-10-21T15:59:30.000+09:00", "한국경제TV","http://v.media.daum.net/v/20170621155930002",457,"http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg", 185,"https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F", "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp","AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’", "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634"),
                    Document("_SBS","news", "2017-11-21T15:59:30.000+09:00", "한국경제TV","http://v.media.daum.net/v/20170621155930002",457,"http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg", 185,"https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F", "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp","AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’", "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634"),
                    Document("_SBS","news", "2017-12-21T15:59:30.000+09:00", "한국경제TV","http://v.media.daum.net/v/20170621155930002",457, "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg", 185,"https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F", "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp","AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’", "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634"),
                    Document("_SBS","news", "2017-06-21T15:39:30.000+09:00", "한국경제TV","http://v.media.daum.net/v/20170621155930002",457, "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg", 185,"https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F", "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp","AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’", "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634"),
                    Document("_SBS","news", "2017-06-21T15:49:30.000+09:00", "한국경제TV","http://v.media.daum.net/v/20170621155930002",457, "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg", 185,"https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F", "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp","AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’", "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634"),
                    Document("_SBS","news", "2017-06-21T15:29:30.000+09:00", "한국경제TV","http://v.media.daum.net/v/20170621155930002",457, "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg", 185,"https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F", "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp","AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’", "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634"),
                    Document("_SBS","news", "2017-06-21T15:19:30.000+09:00", "한국경제TV","http://v.media.daum.net/v/20170621155930002",457, "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg", 185,"https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F", "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp","AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’", "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634")
                )

                SearchSection(
                    modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp),
                    onSearched = {}
                )
                ListSection(
                    modifier = Modifier
                        .padding(4.dp, 4.dp)
                        .weight(1f),
                    state = rememberListSectionState(photos =  photos),
                    onItemClicked = { _, _ -> }
                )
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
                    Text(stringResource(id = R.string.up_to_top))
                }
            }
        }
    }
}