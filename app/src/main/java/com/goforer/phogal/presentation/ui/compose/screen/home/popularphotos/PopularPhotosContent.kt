package com.goforer.phogal.presentation.ui.compose.screen.home.popularphotos

import android.content.res.Configuration
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.repository.Repository.Companion.FIRST_PAGE
import com.goforer.phogal.data.repository.Repository.Companion.ITEM_COUNT
import com.goforer.phogal.data.repository.Repository.Companion.POPULAR
import com.goforer.phogal.presentation.stateholder.business.home.popularphotos.PopularPhotosViewModel
import com.goforer.phogal.presentation.stateholder.uistate.home.popularphotos.PopularPhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.popularphotos.rememberPopularPhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.popularphotos.rememberPopularPhotosSectionState
import com.goforer.phogal.presentation.stateholder.uistate.rememberBaseUiState
import com.goforer.phogal.presentation.ui.theme.PhogalTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PopularPhotosContent(
    modifier: Modifier = Modifier,
    popularPhotosViewModel: PopularPhotosViewModel = hiltViewModel(),
    state: PopularPhotosContentState = rememberPopularPhotosContentState(
        baseUiState = rememberBaseUiState(),
        popularPhotosUiState = popularPhotosViewModel.popularPhotosUiState,
        enabledLoadState = rememberSaveable { mutableStateOf(true) },
        refreshingState = popularPhotosViewModel.isRefreshing
    ),
    onItemClicked: (id: String) -> Unit,
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onShowSnackBar: (text: String) -> Unit,
    onOpenWebView: (firstName: String, url: String) -> Unit,
    onSuccess: (isSuccessful: Boolean) -> Unit
) {
    if (state.enabledLoadState.value) {
        state.enabledLoadState.value = false
        popularPhotosViewModel.trigger(1, Params(POPULAR, FIRST_PAGE, ITEM_COUNT))
    }

    if (state.popularPhotosUiState.collectAsStateWithLifecycle().value is PagingData<*>) {
        PopularPhotosSection(
            modifier = modifier.padding(top = 0.5.dp),
            state = rememberPopularPhotosSectionState(
                scope = state.baseUiState.scope,
                popularPhotosUiState = state.popularPhotosUiState,
                refreshingState = state.refreshingState.collectAsStateWithLifecycle()
            ),
            onItemClicked = { photo, _ ->
                state.enabledLoadState.value= false
                onItemClicked(photo.id)
            },
            onViewPhotos = onViewPhotos,
            onShowSnackBar = onShowSnackBar,
            onOpenWebView = onOpenWebView,
            onSuccess = onSuccess
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
fun PopularPhotosContentPreview(modifier: Modifier = Modifier) {
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
                BoxWithConstraints(modifier = Modifier.weight(1f)) {
                }
            }
        }
    }
}