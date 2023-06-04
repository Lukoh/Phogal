package com.goforer.phogal.presentation.ui.compose.screen.home.gallery.searchphotos

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
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
import com.goforer.phogal.R
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.data.repository.Repository.Companion.FIRST_PAGE
import com.goforer.phogal.data.repository.Repository.Companion.ITEM_COUNT
import com.goforer.phogal.presentation.stateholder.business.home.gallery.photos.GalleryViewModel
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.searchphotos.SearchPhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.searchphotos.rememberPermissionState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.searchphotos.rememberSearchPhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.searchphotos.rememberSearchPhotosSectionState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.searchphotos.rememberSearchSectionState
import com.goforer.phogal.presentation.stateholder.uistate.rememberBaseUiState
import com.goforer.phogal.presentation.ui.compose.screen.home.gallery.common.NoSearchResult
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray7
import com.goforer.phogal.presentation.ui.theme.PhogalTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalComposeUiApi::class, ExperimentalPermissionsApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun SearchPhotosContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    galleryViewModel: GalleryViewModel = hiltViewModel(),
    state: SearchPhotosContentState = rememberSearchPhotosContentState(
        baseUiState = rememberBaseUiState(),
        photosUiState = galleryViewModel.photosUiState,
        isRefreshing = galleryViewModel.isRefreshing
    ),
    onItemClicked: (id: String) -> Unit,
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onShowSnackBar: (text: String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(
                0.dp,
                contentPadding.calculateTopPadding(),
                0.dp,
                0.dp
            )
            .clickable {
                state.baseUiState.keyboardController?.hide()
            },
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
                        galleryViewModel.trigger(2, Params(keyword, ITEM_COUNT))
                    }
                }
            }
        )

        if (state.photosUiState.collectAsStateWithLifecycle().value is PagingData<*>) {
            SearchPhotosSection(
                modifier = Modifier
                    .padding(2.dp, 4.dp)
                    .weight(1f),
                state = rememberSearchPhotosSectionState(
                    scope = state.baseUiState.scope,
                    photosUiState = state.photosUiState,
                    refreshingState = state.isRefreshing.collectAsStateWithLifecycle()
                ),
                onItemClicked = { photo, _ ->
                    onItemClicked(photo.id)
                },
                onRefresh = {
                    galleryViewModel.trigger(2, Params(state.searchKeyword.value, FIRST_PAGE, ITEM_COUNT))
                },
                onViewPhotos = onViewPhotos,
                onShowSnackBar = onShowSnackBar
            )
        } else {
            NoSearchResult(modifier = Modifier.weight(1f))
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
                        style = typography.titleMedium.copy(color = ColorSystemGray7),
                        modifier = Modifier.align(Alignment.Center),
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}