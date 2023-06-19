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
import com.goforer.base.designsystem.component.Chips
import com.goforer.phogal.R
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.data.repository.Repository.Companion.FIRST_PAGE
import com.goforer.phogal.data.repository.Repository.Companion.ITEM_COUNT
import com.goforer.phogal.presentation.stateholder.business.home.gallery.photos.GalleryViewModel
import com.goforer.phogal.presentation.stateholder.business.home.gallery.photos.search.SearchWordViewModel
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.searchphotos.SearchPhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.searchphotos.SearchSectionState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.searchphotos.rememberPermissionState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.searchphotos.rememberSearchPhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.searchphotos.rememberSearchPhotosSectionState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.searchphotos.rememberSearchSectionState
import com.goforer.phogal.presentation.stateholder.uistate.rememberBaseUiState
import com.goforer.phogal.presentation.ui.compose.screen.home.common.InitScreen
import com.goforer.phogal.presentation.ui.theme.Black
import com.goforer.phogal.presentation.ui.theme.Blue70
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray7
import com.goforer.phogal.presentation.ui.theme.PhogalTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import timber.log.Timber

@OptIn(ExperimentalComposeUiApi::class, ExperimentalPermissionsApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun SearchPhotosContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    galleryViewModel: GalleryViewModel = hiltViewModel(),
    keywordViewModel: SearchWordViewModel = hiltViewModel(),
    photosContentState: SearchPhotosContentState = rememberSearchPhotosContentState(
        baseUiState = rememberBaseUiState(),
        photosUiState = galleryViewModel.photosUiState,
        isRefreshing = galleryViewModel.isRefreshing
    ),
    searchState: SearchSectionState = rememberSearchSectionState(searchEnabled = photosContentState.enabledSearch),
    onItemClicked: (id: String) -> Unit,
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onShowSnackBar: (text: String) -> Unit,
    onOpenWebView: (firstName: String, url: String) -> Unit
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
                photosContentState.baseUiState.keyboardController?.hide()
            }
    ) {
        SearchSection(
            modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp),
            state = searchState,
            onSearched = { keyword ->
                if (keyword.isNotEmpty() && keyword != photosContentState.searchWord.value) {
                    photosContentState.searchWord.value = keyword
                    photosContentState.baseUiState.keyboardController?.hide()
                    galleryViewModel.trigger(1, Params(keyword, ITEM_COUNT))
                    keywordViewModel.setWord(photosContentState.searchWord.value)
                    photosContentState.triggeredSearch.value = true
                }
            }
        )
        if (!photosContentState.isScrolling.value) {
            keywordViewModel.getWords()?.let { words ->
                val items = if (photosContentState.triggeredSearch.value) {
                    listOf(words[0])
                } else
                    words

                Chips(
                    modifier = Modifier.padding(top = 8.dp),
                    items = items,
                    textColor = Black,
                    leadingIconTint = Blue70
                ) { keyword ->
                    searchState.editableInputState.textState = keyword
                    photosContentState.searchWord.value = keyword
                    photosContentState.baseUiState.keyboardController?.hide()
                    galleryViewModel.trigger(1, Params(keyword, ITEM_COUNT))
                }

                photosContentState.triggeredSearch.value = false
            }
        }

        if (photosContentState.photosUiState.collectAsStateWithLifecycle().value is PagingData<*>) {
            SearchPhotosSection(
                modifier = Modifier
                    .padding(start = 2.dp, top = 2.dp, end = 2.dp)
                    .weight(1f),
                state = rememberSearchPhotosSectionState(
                    scope = photosContentState.baseUiState.scope,
                    photosUiState = photosContentState.photosUiState,
                    refreshingState = photosContentState.isRefreshing.collectAsStateWithLifecycle()
                ),
                onItemClicked = { photo, _ ->
                    onItemClicked(photo.id)
                },
                onRefresh = {
                    galleryViewModel.trigger(1, Params(photosContentState.searchWord.value, FIRST_PAGE, ITEM_COUNT))
                },
                onViewPhotos = onViewPhotos,
                onShowSnackBar = onShowSnackBar,
                onLoadSuccess = {
                    Timber.d("Search is successful")
                },
                onScroll = {
                    photosContentState.isScrolling.value = it
                },
                onOpenWebView = onOpenWebView
            )
        } else {
            InitScreen(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.search_photos)
            )
        }
    }

    val multiplePermissionsState = rememberMultiplePermissionsState(photosContentState.permissions)

    with(photosContentState) {
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
                state = rememberPermissionState(rationaleTextState = rationaleTextState),
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