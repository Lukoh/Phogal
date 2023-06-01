package com.goforer.phogal.presentation.stateholder.uistate.home.gallery.common

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Stable
class UserInfoState(
    val openBottomSheetState: MutableState<Boolean>,
    val scope: CoroutineScope,
    val bottomSheetState: SheetState,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberUserInfoState(
    openBottomSheetState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    skipPartiallyExpanded: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    scope: CoroutineScope = rememberCoroutineScope(),
    bottomSheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false),
): UserInfoState = remember(openBottomSheetState, skipPartiallyExpanded, scope, bottomSheetState) {
    UserInfoState(
        openBottomSheetState = openBottomSheetState,
        scope = scope,
        bottomSheetState = bottomSheetState
    )
}