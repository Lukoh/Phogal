package com.goforer.phogal.presentation.stateholder.uistate.home.gallery.searchphotos

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
class PermissionState(
    val openBottomSheetState: MutableState<Boolean>,
    val scope: CoroutineScope,
    val bottomSheetState: SheetState,
    val rationaleTextState: MutableState<String>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberPermissionState(
    openBottomSheetState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    skipPartiallyExpanded: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    scope: CoroutineScope = rememberCoroutineScope(),
    bottomSheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false),
    rationaleTextState: MutableState<String> = rememberSaveable { mutableStateOf("") }
): PermissionState = remember(openBottomSheetState, skipPartiallyExpanded, scope, bottomSheetState, rationaleTextState) {
    PermissionState(
        openBottomSheetState = openBottomSheetState,
        scope = scope,
        bottomSheetState = bottomSheetState,
        rationaleTextState = rationaleTextState
    )
}