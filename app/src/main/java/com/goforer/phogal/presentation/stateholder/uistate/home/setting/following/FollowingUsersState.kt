package com.goforer.phogal.presentation.stateholder.uistate.home.setting.following

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import com.goforer.phogal.data.model.remote.response.gallery.common.UserUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Stable
class FollowingUsersState(
    val uiState: StateFlow<MutableList<UserUiState>>,
    var enabledLoadState: MutableState<Boolean>,
)

@Composable
fun rememberFollowingUsersState(
    uiState: StateFlow<MutableList<UserUiState>> = remember { MutableStateFlow(mutableListOf()) },
    enabledLoadState: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) }
): FollowingUsersState = remember(uiState, enabledLoadState) {
    FollowingUsersState(
        uiState = uiState,
        enabledLoadState = enabledLoadState
    )
}