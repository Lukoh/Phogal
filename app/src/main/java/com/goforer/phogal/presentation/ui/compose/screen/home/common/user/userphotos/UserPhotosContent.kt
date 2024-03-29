package com.goforer.phogal.presentation.ui.compose.screen.home.common.user.userphotos

import android.content.res.Configuration
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goforer.base.customtab.openCustomTab
import com.goforer.phogal.R
import com.goforer.phogal.presentation.stateholder.uistate.home.common.user.photos.UserPhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.common.user.photos.rememberUserPhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.common.user.photos.rememberUserPhotosSectionState
import com.goforer.phogal.presentation.ui.compose.screen.home.gallery.SearchSection
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray7
import com.goforer.phogal.presentation.ui.theme.PhogalTheme

@Composable
fun UserPhotosContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    state: UserPhotosContentState = rememberUserPhotosContentState(),
    onTriggered: (triggered: Boolean) -> Unit,
    onItemClicked: (id: String) -> Unit,
    onShowSnackBar: (text: String) -> Unit,
    onSuccess: (isSuccessful: Boolean) -> Unit
) {
    val triggered by rememberUpdatedState(onTriggered)

    triggered(state.enabledLoadState.value)
    state.enabledLoadState.value = false
    UserPhotosSection(
        modifier = modifier
            .padding(top = 0.5.dp),
        contentPadding = contentPadding,
        state = rememberUserPhotosSectionState(
            uiState = state.uiState,
            refreshingState = state.refreshingState.collectAsStateWithLifecycle()
        ),
        onItemClicked = { id, _ ->
            state.enabledLoadState.value = false
            onItemClicked(id)
        },
        onViewPhotos = { _, _, _, _ -> },
        onShowSnackBar = onShowSnackBar,
        onOpenWebView = { _, url ->
            openCustomTab(state.baseUiState.context, url)
        },
        onSuccess = {
            if (it)
                state.enabledLoadState.value = false

            onSuccess(it)
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
                    modifier = Modifier.padding(2.dp, 0.dp, 2.dp, 0.dp),
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