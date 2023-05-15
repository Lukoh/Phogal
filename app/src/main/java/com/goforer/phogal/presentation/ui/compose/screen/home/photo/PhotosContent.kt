package com.goforer.phogal.presentation.ui.compose.screen.home.photo

import android.Manifest
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.goforer.phogal.data.network.response.Status
import com.goforer.phogal.data.repository.Repository.Companion.FIRST_PAGE
import com.goforer.phogal.data.repository.Repository.Companion.ITEM_COUNT
import com.goforer.phogal.presentation.stateholder.business.home.photo.PhotoViewModel
import com.goforer.phogal.presentation.stateholder.uistate.home.photos.PhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.photos.rememberListSectionState
import com.goforer.phogal.presentation.stateholder.uistate.home.photos.rememberPermissionState
import com.goforer.phogal.presentation.stateholder.uistate.home.photos.rememberPhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.photos.rememberSearchSectionState
import com.goforer.phogal.presentation.stateholder.uistate.rememberBaseUiState
import com.goforer.phogal.presentation.ui.theme.Blue40
import com.goforer.phogal.presentation.ui.theme.PhogalTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber

@OptIn(ExperimentalComposeUiApi::class, ExperimentalPermissionsApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun PhotosContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    photoViewModel: PhotoViewModel = hiltViewModel(),
    state: PhotosContentState = rememberPhotosContentState(baseUiState = rememberBaseUiState()),
    onItemClicked: (item: Document, index: Int) -> Unit
) {
    var searched by rememberSaveable { mutableStateOf(false) }
    var searchedKeyword by rememberSaveable { mutableStateOf("") }
    val enabledSearch: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
    var showPermissionBottomSheet by rememberSaveable { mutableStateOf(false) }

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
                state = rememberSearchSectionState(searchEnabled = enabledSearch),
                onSearched = { keyword ->
                    if (keyword.isNotEmpty()) {
                        searchedKeyword = keyword
                        state.baseUiState.keyboardController?.hide()
                        photoViewModel.trigger(2, Params(keyword, FIRST_PAGE, ITEM_COUNT))
                        searched = true
                    }
                }
            )
            if (searched) {
                val photosState = photoViewModel.photosStateFlow.collectAsStateWithLifecycle()
                val listSectionState = rememberListSectionState(scope = state.baseUiState.scope)

                when(photosState.value.status) {
                    Status.SUCCESS -> {
                        @Suppress("UNCHECKED_CAST")
                        val photos = flowOf(photosState.value.data as PagingData<Document>).collectAsLazyPagingItems()
                        listSectionState.refreshing.value = false
                        ListSection(
                            modifier = Modifier
                                .padding(4.dp, 4.dp)
                                .weight(1f),
                            state = listSectionState,
                            photos,
                            onItemClicked = { document, index ->
                                onItemClicked(document, index)
                            },
                            onRefresh = {
                                photoViewModel.trigger(2, Params(searchedKeyword))
                            }
                        )
                    }
                    Status.LOADING -> {
                        // To Do : run the loading animation or shimmer
                        LoadingPhotos(
                            Modifier
                                .padding(4.dp, 8.dp)
                                .weight(1f), 3)
                    }
                    Status.ERROR -> {
                        // To Do : handle the error
                        Timber.d("Error Code - %d & Error Message - %s", photosState.value.errorCode, photosState.value.message)
                    }
                }
            } else {
                BoxWithConstraints(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(id = R.string.search_photos),
                        style = typography.titleMedium.copy(color = Blue40),
                        modifier = Modifier.align(Alignment.Center),
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA
        )
    )
    val rationaleTextState: MutableState<String> = rememberSaveable { mutableStateOf("") }

    CheckPermission(
        multiplePermissionsState= multiplePermissionsState,
        onPermissionGranted = {
            enabledSearch.value = true
            showPermissionBottomSheet = false
        },
        onPermissionNotGranted = {
            rationaleTextState.value = it
            enabledSearch.value = false
            showPermissionBottomSheet = true
        }
    )

    if (showPermissionBottomSheet) {
        PermissionBottomSheet(
            permissionState = rememberPermissionState(rationaleTextState = rationaleTextState),
            onDismissedRequest = {
                enabledSearch.value = false
                showPermissionBottomSheet = false
            },
            onClicked = {
                multiplePermissionsState.launchMultiplePermissionRequest()
                showPermissionBottomSheet = false
            }
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
                        style = typography.titleMedium.copy(color = Blue40),
                        modifier = Modifier.align(Alignment.Center),
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}