package com.goforer.phogal.presentation.ui.compose.screen.home.setting.following

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.goforer.phogal.data.model.remote.response.gallery.common.UserUiState
import com.goforer.phogal.presentation.analytics.TrackScreenViewEvent
import com.goforer.phogal.presentation.stateholder.business.home.common.follow.FollowViewModel
import com.goforer.phogal.presentation.stateholder.uistate.home.setting.following.rememberFollowingUserItemState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FollowingUsersSection(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    followViewModel: FollowViewModel = hiltViewModel(),
    users: MutableList<UserUiState>,
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onOpenWebView: (firstName: String, url: String?) -> Unit,
    onFollow: (userUiState: UserUiState) -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .padding(
                0.dp,
                contentPadding.calculateTopPadding(),
                0.dp,
                0.dp
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
        ) {
            itemsIndexed(
                items = users,
                key = { _, item -> item.id }
            ) {index, item ->
                FollowingUsersItem(
                    modifier = modifier.animateItemPlacement(
                        tween(durationMillis = 250)
                    ),
                    state = rememberFollowingUserItemState(
                        indexState = rememberSaveable { mutableIntStateOf(index) },
                        userUiState = rememberSaveable { mutableStateOf(item) },
                        visibleViewButtonState = rememberSaveable { mutableStateOf(true) },
                        followedState = rememberSaveable { mutableStateOf(followViewModel.isUserFollowed(item)) }
                    ),
                    onViewPhotos = onViewPhotos,
                    onOpenWebView = onOpenWebView,
                    onFollow = onFollow
                )

                if (index == users.size - 1)
                    Spacer(modifier = Modifier.height(26.dp))
            }
        }

        TrackScreenViewEvent(screenName = "View_My_Following")
    }
}