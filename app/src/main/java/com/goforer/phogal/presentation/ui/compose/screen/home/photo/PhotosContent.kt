package com.goforer.phogal.presentation.ui.compose.screen.home.photo

import android.content.res.Configuration
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.goforer.phogal.R
import com.goforer.phogal.data.model.remote.response.photos.Document
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.data.repository.Repository.Companion.FIRST_PAGE
import com.goforer.phogal.data.repository.Repository.Companion.ITEM_COUNT
import com.goforer.phogal.presentation.stateholder.business.home.photo.PhotoViewModel
import com.goforer.phogal.presentation.stateholder.uistate.home.photos.PhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.photos.rememberListSectionState
import com.goforer.phogal.presentation.stateholder.uistate.home.photos.rememberPermissionState
import com.goforer.phogal.presentation.stateholder.uistate.home.photos.rememberPhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.photos.rememberSearchSectionState
import com.goforer.phogal.presentation.stateholder.uistate.rememberBaseUiState
import com.goforer.phogal.presentation.ui.theme.DarkGreen30
import com.goforer.phogal.presentation.ui.theme.PhogalTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalComposeUiApi::class, ExperimentalPermissionsApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun PhotosContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    photoViewModel: PhotoViewModel = hiltViewModel(),
    state: PhotosContentState = rememberPhotosContentState(
        baseUiState = rememberBaseUiState(),
        photosUiState = photoViewModel.photosUiState,
        isRefreshing = photoViewModel.isRefreshing
    ),
    onItemClicked: (item: Document, index: Int) -> Unit
) {
    val photosUiState = state.photosUiState.collectAsStateWithLifecycle()
    val isRefreshing = state.isRefreshing.collectAsStateWithLifecycle()

    BoxWithConstraints(modifier = modifier) {
        Column(
            modifier = modifier
                .padding(
                    0.dp,
                    contentPadding.calculateTopPadding(),
                    0.dp,
                    0.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchSection(
                modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp),
                state = rememberSearchSectionState(searchEnabled = state.enabledSearch),
                onSearched = { keyword ->
                    if (keyword.isNotEmpty()) {
                        with(state) {
                            searchKeyword.value = keyword
                            baseUiState.keyboardController?.hide()
                            photoViewModel.trigger(2, Params(keyword, FIRST_PAGE, ITEM_COUNT))
                        }
                    }
                }
            )

            if (photosUiState.value is PagingData<*>) {
                @Suppress("UNCHECKED_CAST")
                val photos = flowOf(photosUiState.value as PagingData<Document>).collectAsLazyPagingItems()

                ListSection(
                    modifier = Modifier
                        .padding(4.dp, 4.dp)
                        .weight(1f),
                    state = rememberListSectionState(scope = state.baseUiState.scope, refreshing = isRefreshing as MutableState<Boolean>),
                    photos = photos,
                    onItemClicked = { document, index ->
                        onItemClicked(document, index)
                    },
                    onRefresh = {
                        photoViewModel.trigger(2, Params(state.searchKeyword.value, FIRST_PAGE, ITEM_COUNT))
                    }
                )
            } else {
                BoxWithConstraints(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(id = R.string.search_photos),
                        style = typography.titleMedium.copy(color = DarkGreen30),
                        modifier = Modifier.align(Alignment.Center),
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    val multiplePermissionsState = rememberMultiplePermissionsState(state.permissions)

    with(state) {
        CheckPermission(
            multiplePermissionsState = multiplePermissionsState,
            onPermissionGranted = {
                enabledSearch.value = true
                showPermissionBottomSheet.value = false
            },
            onPermissionNotGranted = {
                rationaleTextState.value = it
                enabledSearch.value = false
                showPermissionBottomSheet.value = true
            }
        )
        if (showPermissionBottomSheet.value) {
            PermissionBottomSheet(
                permissionState = rememberPermissionState(rationaleTextState = rationaleTextState),
                onDismissedRequest = {
                    enabledSearch.value = false
                    showPermissionBottomSheet.value = false
                },
                onClicked = {
                    multiplePermissionsState.launchMultiplePermissionRequest()
                    showPermissionBottomSheet.value = false
                }
            )
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
                        0.dp,
                        0.dp,
                        0.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchSection(
                    modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp),
                    onSearched = { }
                )
                BoxWithConstraints(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(id = R.string.search_photos),
                        style = typography.titleMedium.copy(color = DarkGreen30),
                        modifier = Modifier.align(Alignment.Center),
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}