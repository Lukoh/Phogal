package com.goforer.phogal.presentation.ui.compose.screen.home.gallery

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import com.goforer.base.designsystem.animation.GenericCubicAnimationShape
import com.goforer.base.designsystem.component.Chips
import com.goforer.phogal.R
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.repository.Repository.Companion.ITEM_COUNT
import com.goforer.phogal.presentation.stateholder.business.home.gallery.GalleryViewModel
import com.goforer.phogal.presentation.stateholder.business.home.gallery.search.SearchWordViewModel
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.SearchPhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.SearchSectionState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.rememberPermissionState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.rememberSearchPhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.rememberSearchPhotosSectionState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.rememberSearchSectionState
import com.goforer.phogal.presentation.stateholder.uistate.rememberBaseUiState
import com.goforer.phogal.presentation.ui.compose.screen.home.common.InitScreen
import com.goforer.phogal.presentation.ui.theme.Black
import com.goforer.phogal.presentation.ui.theme.Blue70
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
    galleryViewModel: GalleryViewModel = hiltViewModel(),
    searchWordViewModel: SearchWordViewModel = hiltViewModel(),
    photosContentState: SearchPhotosContentState = rememberSearchPhotosContentState(
        baseUiState = rememberBaseUiState(),
        photosUiState = galleryViewModel.photosUiState,
        refreshingState = galleryViewModel.isRefreshing
    ),
    searchState: SearchSectionState = rememberSearchSectionState(enabledState = photosContentState.enabledState),
    onItemClicked: (id: String) -> Unit,
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onShowSnackBar: (text: String) -> Unit,
    onOpenWebView: (firstName: String, url: String) -> Unit,
    onSuccess: (isSuccessful: Boolean) -> Unit
) {
    Column(
        modifier = modifier
            .clickable {
                photosContentState.baseUiState.keyboardController?.hide()
            }
    ) {
        SearchSection(
            modifier = Modifier.padding(2.dp, 0.dp, 2.dp, 0.dp),
            state = searchState,
            onSearched = { keyword ->
                if (keyword.isNotEmpty() && keyword != photosContentState.wordState.value) {
                    photosContentState.wordState.value = keyword
                    photosContentState.baseUiState.keyboardController?.hide()
                    searchWordViewModel.setWord(keyword)
                    photosContentState.triggeredState.value = true
                    galleryViewModel.trigger(1, Params(keyword, ITEM_COUNT))
                }
            }
        )
        GenericCubicAnimationShape(
            visible = !photosContentState.scrollingState.value,
            duration = 250
        ) { animatedShape, visible ->
            if (visible) {
                searchWordViewModel.getWords()?.let { words ->
                    val items = if (photosContentState.triggeredState.value) {
                        listOf(words[0])
                    } else
                        words

                    Chips(
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .graphicsLayer {
                                clip = true
                                shape = animatedShape
                            },
                        items = items,
                        textColor = Black,
                        leadingIconTint = Blue70
                    ) { keyword ->
                        searchState.editableInputState.textState = keyword
                        photosContentState.wordState.value = keyword
                        photosContentState.baseUiState.keyboardController?.hide()
                        galleryViewModel.trigger(1, Params(keyword, ITEM_COUNT))
                    }

                    photosContentState.triggeredState.value = false
                }
            }
        }

        if (photosContentState.photosUiState.collectAsStateWithLifecycle().value is PagingData<*>) {
            SearchPhotosSection(
                modifier = Modifier
                    .padding(top = 0.5.dp)
                    .weight(1f),
                state = rememberSearchPhotosSectionState(
                    scope = photosContentState.baseUiState.scope,
                    photosUiState = photosContentState.photosUiState,
                    refreshingState = photosContentState.refreshingState.collectAsStateWithLifecycle()
                ),
                onItemClicked = { photo, _ ->
                    onItemClicked(photo.id)
                },
                onViewPhotos = onViewPhotos,
                onShowSnackBar = onShowSnackBar,
                onLoadSuccess = {
                    onSuccess(it)
                },
                onScroll = {
                    photosContentState.scrollingState.value = it
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
                enabledState.value = true
                permissionState.value = false
            },
            onPermissionNotGranted = {
                rationaleTextState.value = it
                enabledState.value = false
                permissionState.value = true
            }
        )
        if (permissionState.value) {
            PermissionBottomSheet(
                state = rememberPermissionState(rationaleTextState = rationaleTextState),
                onDismissedRequest = {
                    enabledState.value = false
                    permissionState.value = false
                },
                onClicked = {
                    multiplePermissionsState.launchMultiplePermissionRequest()
                    permissionState.value = false
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