package com.goforer.phogal.presentation.stateholder.uistate.home.setting.following

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable

@Stable
class FollowingUserItemState(
    val index: MutableState<Int>,
    val user: State<Any>,
    val visibleViewPhotosButton: MutableState<Boolean>,
    val isClicked: MutableState<Boolean>,
    val isUserFollowed: MutableState<Boolean>
)

@Composable
fun rememberFollowingUserItemState(
    index: MutableState<Int> = rememberSaveable { mutableIntStateOf(0) },
    user: State<Any> = remember { mutableStateOf(Any()) },
    visibleViewPhotosButton: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    isClicked: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    isUserFollowed: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
): FollowingUserItemState = remember(index, user, visibleViewPhotosButton, isClicked) {
    FollowingUserItemState(
        index = index,
        visibleViewPhotosButton = visibleViewPhotosButton,
        user = user,
        isClicked = isClicked,
        isUserFollowed = isUserFollowed
    )
}