package com.goforer.phogal.presentation.ui.compose.screen.home.common.user.userphotos

import android.content.res.Configuration
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import com.goforer.base.customtab.openCustomTab
import com.goforer.phogal.R
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.repository.Repository
import com.goforer.phogal.presentation.stateholder.business.home.common.user.UserPhotosViewModel
import com.goforer.phogal.presentation.stateholder.uistate.home.common.user.photos.UserPhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.common.user.photos.rememberUserPhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.common.user.photos.rememberUserPhotosSectionState
import com.goforer.phogal.presentation.ui.compose.screen.home.common.InitScreen
import com.goforer.phogal.presentation.ui.compose.screen.home.gallery.SearchSection
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray7
import com.goforer.phogal.presentation.ui.theme.PhogalTheme

@Composable
fun UserPhotosContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    userPhotosViewModel: UserPhotosViewModel = hiltViewModel(),
    state: UserPhotosContentState = rememberUserPhotosContentState(
        photosUiState = userPhotosViewModel.photosUiState,
        refreshingState = userPhotosViewModel.isRefreshing,
    ),
    onItemClicked: (id: String) -> Unit,
    onShowSnackBar: (text: String) -> Unit,
    onSuccess: (isSuccessful: Boolean) -> Unit
) {
    if (state.enabledLoadState.value) {
        state.enabledLoadState.value = false
        userPhotosViewModel.trigger(1, Params(state.nameState.value, Repository.ITEM_COUNT))
    }

    if (state.photosUiState.collectAsStateWithLifecycle().value is PagingData<*>) {
        UserPhotosSection(
            modifier = Modifier
                .padding(start = 2.dp, top = 2.dp, end = 2.dp),
            contentPadding = contentPadding,
            state = rememberUserPhotosSectionState(
                photosUiState = state.photosUiState,
                refreshingState = state.refreshingState.collectAsStateWithLifecycle()
            ),
            onItemClicked = { photo, _ ->
                state.enabledLoadState.value = false
                onItemClicked(photo.id)
            },
            onViewPhotos = { _, _, _, _ -> },
            onShowSnackBar = onShowSnackBar,
            onOpenWebView = { _, url ->
                state.baseUiState.context?.let { openCustomTab(it, url) }
             },
            onSuccess = onSuccess
        )
    } else {
        InitScreen(
            modifier = modifier,
            text = stringResource(id = R.string.search_photos)
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