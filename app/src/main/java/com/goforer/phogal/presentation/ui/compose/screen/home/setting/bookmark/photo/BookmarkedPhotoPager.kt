package com.goforer.phogal.presentation.ui.compose.screen.home.setting.bookmark.photo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import coil.size.Size
import com.goforer.base.designsystem.component.loadImagePainter
import com.goforer.phogal.data.model.remote.response.gallery.common.PhotoUiState
import com.goforer.phogal.presentation.analytics.TrackScreenViewEvent
import com.goforer.phogal.presentation.ui.compose.screen.home.common.photo.ImageContent
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookmarkedPhotoScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    photos: List<PhotoUiState>
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        val pageCount = photos.size - 1
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f,
        ) {
            pageCount
        }

        HorizontalPager(
            modifier = Modifier,
            state = pagerState,
            pageSpacing = 0.dp,
            userScrollEnabled = true,
            reverseLayout = false,
            contentPadding = contentPadding,
            beyondBoundsPageCount = 2,
            pageSize = PageSize.Fill,
            flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
            key = null,
            pageNestedScrollConnection = remember(pagerState) {
                PagerDefaults.pageNestedScrollConnection(Orientation.Horizontal)
            }
        ) { page ->
            Column(
                modifier = Modifier
                    .padding(top = contentPadding.calculateTopPadding())
                    .verticalScroll(rememberScrollState())
                    .graphicsLayer {
                        val pageOffset = pagerState.currentPageOffsetFraction

                        translationX = pageOffset * size.width
                        alpha = 1 - pageOffset.absoluteValue
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val imageUrl = photos[page].urls.raw
                val painter = loadImagePainter(
                    data = imageUrl,
                    size = Size(photos[page].width.div(8), photos[page].height.div(8))
                )

                ImageContent(painter = painter)
            }

            CircularProgressIndicator()
        }
    }

    TrackScreenViewEvent(screenName = "Bookmark Photos")
}