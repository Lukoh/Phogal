package com.goforer.phogal.presentation.ui.compose.screen.home.setting.following

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goforer.phogal.R
import com.goforer.phogal.presentation.stateholder.business.home.common.follow.FollowViewModel
import com.goforer.phogal.presentation.ui.compose.screen.home.common.InitScreen

@Composable
fun FollowingUsersContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    followViewModel: FollowViewModel = hiltViewModel(),
    enabledLoadPhotos: Boolean,
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onOpenWebView: (firstName: String, url: String?) -> Unit
) {
    val users by followViewModel.uiState.collectAsStateWithLifecycle()

    followViewModel.trigger(1)
    if (users.isNotEmpty()) {
        FollowingUsersSection(
            modifier = modifier,
            contentPadding = contentPadding,
            users = users,
            onViewPhotos = onViewPhotos,
            onOpenWebView = onOpenWebView,
            onFollow = {
                with(followViewModel) {
                    setUserFollow(it)
                    trigger(1)
                }
            }
        )
    } else {
        if (enabledLoadPhotos) {
            BoxWithConstraints(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                InitScreen(
                    modifier = Modifier,
                    text = stringResource(id = R.string.setting_no_following)
                )
            }
        }
    }
}