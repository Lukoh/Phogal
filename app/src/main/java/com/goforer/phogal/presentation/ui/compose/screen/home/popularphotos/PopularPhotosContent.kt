package com.goforer.phogal.presentation.ui.compose.screen.home.popularphotos

import android.content.res.Configuration
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goforer.phogal.presentation.stateholder.uistate.home.popularphotos.PopularPhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.popularphotos.rememberPopularPhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.popularphotos.rememberPopularPhotosSectionState
import com.goforer.phogal.presentation.ui.theme.PhogalTheme

@Composable
fun PopularPhotosContent(
    modifier: Modifier = Modifier,
    state: PopularPhotosContentState = rememberPopularPhotosContentState(),
    onTriggered: (enabled: Boolean) -> Unit,
    onItemClicked: (id: String) -> Unit,
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onShowSnackBar: (text: String) -> Unit,
    onOpenWebView: (firstName: String, url: String) -> Unit
) {
    val triggered by rememberUpdatedState(onTriggered)

    triggered(state.enabledLoadState.value)
    state.enabledLoadState.value = false
    PopularPhotosSection(
        modifier = modifier,
        state = rememberPopularPhotosSectionState(
            scope = state.baseUiState.scope,
            uiState = state.uiState,
            refreshingState = state.refreshingState.collectAsStateWithLifecycle()
        ),
        onItemClicked = { id, _ ->
            state.enabledLoadState.value= false
            onItemClicked(id)
        },
        onViewPhotos = onViewPhotos,
        onShowSnackBar = onShowSnackBar,
        onOpenWebView = onOpenWebView,
        onSuccess = {
            state.visibleActionsState.value = it
        }
    )
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