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
    val indexState: MutableState<Int>,
    val userState: State<Any>,
    val visibleViewButtonState: MutableState<Boolean>,
    val clickedState: MutableState<Boolean>,
    val followedState: MutableState<Boolean>
)

@Composable
fun rememberFollowingUserItemState(
    indexState: MutableState<Int> = rememberSaveable { mutableIntStateOf(0) },
    userState: State<Any> = remember { mutableStateOf(Any()) },
    visibleViewButtonState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    clickedState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    followedState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
): FollowingUserItemState = remember(indexState, userState, visibleViewButtonState, clickedState) {
    FollowingUserItemState(
        indexState = indexState,
        userState = userState,
        visibleViewButtonState = visibleViewButtonState,
        clickedState = clickedState,
        followedState = followedState
    )
}