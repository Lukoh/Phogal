package com.goforer.phogal.presentation.ui.compose.screen.home.photo

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.goforer.phogal.data.model.response.Document
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.data.network.response.Status
import com.goforer.phogal.presentation.stateholder.business.home.photo.PhotoViewModel
import com.goforer.phogal.presentation.stateholder.uistate.home.photos.PhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.photos.rememberListSectionState
import com.goforer.phogal.presentation.stateholder.uistate.home.photos.rememberPhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.rememberBaseUiState
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PhotosContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    photoViewModel: PhotoViewModel = hiltViewModel(),
    state: PhotosContentState = rememberPhotosContentState(baseUiState = rememberBaseUiState()),
    onItemClicked: (item: Document, index: Int) -> Unit
) {
    var searched by rememberSaveable { mutableStateOf(false) }

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
                    photoViewModel.trigger(2, Params(keyword))
                    searched = true
                }
            )
            if (searched) {
                val photosState = photoViewModel.photosStateFlow.collectAsStateWithLifecycle()
                @Suppress("UNCHECKED_CAST")
                val photos = flowOf(photosState.value.data as PagingData<Document>).collectAsLazyPagingItems()

                when(photosState.value.status) {
                    Status.SUCCESS -> {
                        state.enabledList.value = true
                        ListSection(
                            modifier = Modifier
                                .padding(4.dp, 4.dp)
                                .weight(1f),
                            state = rememberListSectionState(scope = state.baseUiState.scope),
                            photos,
                            onItemClicked = { document, index ->
                                onItemClicked(document, index)
                            }
                        )
                    }
                    Status.LOADING -> {
                        // To Do : run the loading animation or shimmer
                        state.enabledList.value = false
                    }
                    Status.ERROR -> {
                        // To Do : handle the error
                        state.enabledList.value = false
                        Timber.d("Error Code - %d & Error Message - %s", photosState.value.errorCode, photosState.value.message)
                    }
                }
            }
        }
    }
}