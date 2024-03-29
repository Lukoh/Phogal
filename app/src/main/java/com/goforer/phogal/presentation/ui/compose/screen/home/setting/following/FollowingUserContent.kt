package com.goforer.phogal.presentation.ui.compose.screen.home.setting.following

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goforer.phogal.R
import com.goforer.phogal.data.model.remote.response.gallery.common.UserUiState
import com.goforer.phogal.presentation.stateholder.uistate.home.setting.following.FollowingUsersState
import com.goforer.phogal.presentation.stateholder.uistate.home.setting.following.rememberFollowingUsersState
import com.goforer.phogal.presentation.ui.compose.screen.home.common.InitScreen

@Composable
fun FollowingUsersContent(
    modifier: Modifier = Modifier,
    state: FollowingUsersState = rememberFollowingUsersState(),
    contentPadding: PaddingValues = PaddingValues(4.dp),
    onTriggered: (enabled: Boolean) -> Unit,
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onFollowed: (userUiState: UserUiState) -> Unit,
    onOpenWebView: (firstName: String, url: String?) -> Unit
) {
    val triggered by rememberUpdatedState(onTriggered)
    val users by state.uiState.collectAsStateWithLifecycle()

    triggered(state.enabledLoadState.value)
    state.enabledLoadState.value = false
    if (users.isNotEmpty()) {
        FollowingUsersSection(
            modifier = modifier,
            contentPadding = contentPadding,
            users = users,
            onViewPhotos = onViewPhotos,
            onOpenWebView = onOpenWebView,
            onFollowed = onFollowed
        )
    }

    InitScreen(
        modifier = Modifier,
        text = stringResource(id = R.string.setting_no_following)
    )
}