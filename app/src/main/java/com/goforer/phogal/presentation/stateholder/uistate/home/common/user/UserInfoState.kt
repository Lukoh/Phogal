package com.goforer.phogal.presentation.stateholder.uistate.home.common.user

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
import androidx.compose.ui.ExperimentalComposeUiApi
import com.goforer.phogal.presentation.stateholder.uistate.BaseUiState
import com.goforer.phogal.presentation.stateholder.uistate.rememberBaseUiState
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Stable
class UserInfoState(
    val baseUiState: BaseUiState,
    val openBottomSheetState: MutableState<Boolean>,
    val scope: CoroutineScope,
    val bottomSheetState: SheetState,
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun rememberUserInfoState(
    baseUiState: BaseUiState = rememberBaseUiState(),
    openBottomSheetState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    skipPartiallyExpanded: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    scope: CoroutineScope = rememberCoroutineScope(),
    bottomSheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
): UserInfoState = remember(
    baseUiState, openBottomSheetState, skipPartiallyExpanded, scope, bottomSheetState) {
    UserInfoState(
        baseUiState = baseUiState,
        openBottomSheetState = openBottomSheetState,
        scope = scope,
        bottomSheetState = bottomSheetState
    )
}