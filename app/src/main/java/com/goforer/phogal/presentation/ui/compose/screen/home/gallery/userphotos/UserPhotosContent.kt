package com.goforer.phogal.presentation.ui.compose.screen.home.gallery.userphotos

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
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
import com.goforer.phogal.data.repository.Repository
import com.goforer.phogal.presentation.stateholder.business.home.gallery.user.UserPhotosViewModel
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.userphotos.UserPhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.userphotos.rememberUserPhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.userphotos.rememberUserPhotosSectionState
import com.goforer.phogal.presentation.stateholder.uistate.rememberBaseUiState
import com.goforer.phogal.presentation.ui.compose.screen.home.gallery.common.NoSearchResult
import com.goforer.phogal.presentation.ui.compose.screen.home.gallery.searchphotos.SearchSection
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray7
import com.goforer.phogal.presentation.ui.theme.PhogalTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserPhotosContent(
    modifier: Modifier = Modifier,
    name: String,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    userPhotosViewModel: UserPhotosViewModel = hiltViewModel(),
    state: UserPhotosContentState = rememberUserPhotosContentState(
        baseUiState = rememberBaseUiState(),
        photosUiState = userPhotosViewModel.photosUiState,
        isRefreshing = userPhotosViewModel.isRefreshing
    ),
    onItemClicked: (id: String) -> Unit
) {
    userPhotosViewModel.trigger(2, Params(name, Repository.ITEM_COUNT))
    BoxWithConstraints(
        modifier = modifier
            .padding(
                0.dp,
                contentPadding.calculateTopPadding(),
                0.dp,
                0.dp
            ).clickable {
                state.baseUiState.keyboardController?.hide()
            }
    ) {
        if (state.photosUiState.collectAsStateWithLifecycle().value is PagingData<*>) {
            UserPhotosSection(
                modifier = Modifier
                    .padding(4.dp, 4.dp),
                state = rememberUserPhotosSectionState(
                    scope = state.baseUiState.scope,
                    photosUiState = state.photosUiState,
                    refreshingState = state.isRefreshing.collectAsStateWithLifecycle()
                ),
                onItemClicked = { photo, _ ->
                    onItemClicked(photo.id)
                },
                onRefresh = {
                    userPhotosViewModel.trigger(2, Params(name, Repository.ITEM_COUNT))
                },
                onViewPhotos = { _, _, _, _ -> }
            )
        } else {
            NoSearchResult(modifier = modifier)
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
                        style = MaterialTheme.typography.titleMedium.copy(color = ColorSystemGray7),
                        modifier = Modifier.align(Alignment.Center),
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}