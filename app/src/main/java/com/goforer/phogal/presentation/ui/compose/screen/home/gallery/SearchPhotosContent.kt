package com.goforer.phogal.presentation.ui.compose.screen.home.gallery

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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
import com.goforer.base.designsystem.animation.GenericCubicAnimationShape
import com.goforer.base.designsystem.component.Chips
import com.goforer.base.extension.isNullOnFlow
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
import com.goforer.phogal.presentation.ui.theme.DarkGreen10
import com.goforer.phogal.presentation.ui.theme.PhogalTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchPhotosContent(
    modifier: Modifier = Modifier,
    galleryViewModel: GalleryViewModel = hiltViewModel(),
    searchWordViewModel: SearchWordViewModel = hiltViewModel(),
    photosContentState: SearchPhotosContentState = rememberSearchPhotosContentState(
        baseUiState = rememberBaseUiState(),
        uiState = galleryViewModel.uiState,
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
            onSearched = { word ->
                handleOnSearched(
                    word = word,
                    photosContentState = photosContentState,
                    galleryViewModel = galleryViewModel,
                    searchWordViewModel = searchWordViewModel
                )
            }
        )
        Spacer(
            modifier = Modifier.height(
                if (photosContentState.scrollingState.value)
                    8.dp
                else
                    4.dp
            )
        )
        SearchChips(
            galleryViewModel = galleryViewModel,
            searchWordViewModel = searchWordViewModel,
            photosContentState = photosContentState,
            searchState = searchState
        )
        if (photosContentState.triggeredState.value) {
            SearchPhotosSection(
                modifier = Modifier
                    .padding(top = 0.5.dp)
                    .weight(1f),
                state = rememberSearchPhotosSectionState(
                    scope = photosContentState.baseUiState.scope,
                    uiState = photosContentState.uiState,
                    refreshingState = photosContentState.refreshingState.collectAsStateWithLifecycle()
                ),
                onItemClicked = { id, _ ->
                    onItemClicked(id)
                },
                onViewPhotos = onViewPhotos,
                onShowSnackBar = onShowSnackBar,
                onLoadSuccess = {
                    onSuccess(it)
                    if (!it)
                        photosContentState.triggeredState.value = false
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

    Permissions(photosContentState = photosContentState)
}

@OptIn(ExperimentalComposeUiApi::class)
fun handleOnSearched(
    word: String,
    photosContentState: SearchPhotosContentState,
    galleryViewModel: GalleryViewModel,
    searchWordViewModel: SearchWordViewModel,
) {
    if (word.isNotEmpty() && word != photosContentState.wordState.value) {
        photosContentState.wordState.value = word
        photosContentState.baseUiState.keyboardController?.hide()
        photosContentState.baseUiState.scope.launch {
            searchWordViewModel.setWord(word)
        }

        photosContentState.searchedState.value = true
        photosContentState.triggeredState.value = true
        photosContentState.baseUiState.scope.launch {
            galleryViewModel.trigger(1, Params(word, ITEM_COUNT))
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchChips(
    galleryViewModel: GalleryViewModel = hiltViewModel(),
    searchWordViewModel: SearchWordViewModel = hiltViewModel(),
    photosContentState: SearchPhotosContentState = rememberSearchPhotosContentState(
        baseUiState = rememberBaseUiState(),
        uiState = galleryViewModel.uiState,
        refreshingState = galleryViewModel.isRefreshing
    ),
    searchState: SearchSectionState = rememberSearchSectionState(
        enabledState = photosContentState.enabledState
    )
) {
    GenericCubicAnimationShape(
        visible = !photosContentState.scrollingState.value,
        duration = 250
    ) { animatedShape, visible ->
        val searchedWords = remember { mutableStateListOf<String>() }

        LaunchedEffect(
            visible,
            photosContentState.searchedState.value,
            photosContentState.removedWordState.value
        ) {
            searchWordViewModel.getWords().isNullOnFlow({
                searchedWords.clear()
            }, {
                searchedWords.clear()
                searchedWords.addAll(it)
            })
        }

        if (searchedWords.isNotEmpty()) {
            val items = if (photosContentState.searchedState.value) {
                listOf(searchedWords[0])
            } else
                searchedWords

            if (photosContentState.removedWordState.value)
                photosContentState.removedWordState.value = false

            Chips(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .graphicsLayer {
                        clip = true
                        shape = animatedShape
                    },
                items = items,
                textColor = Black,
                leadingIconTint = Blue70,
                trailingIconTint = DarkGreen10,
                onClicked = { keyword ->
                    searchState.editableInputState.textState = keyword
                    photosContentState.wordState.value = keyword
                    photosContentState.triggeredState.value = true
                    photosContentState.baseUiState.keyboardController?.hide()
                    galleryViewModel.trigger(1, Params(keyword, ITEM_COUNT))
                },
                onDeleted = {
                    photosContentState.baseUiState.scope.launch {
                        searchWordViewModel.removeWord(it)
                    }

                    photosContentState.removedWordState.value = true
                }
            )

            photosContentState.searchedState.value = false
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun Permissions(
    photosContentState: SearchPhotosContentState
) {
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