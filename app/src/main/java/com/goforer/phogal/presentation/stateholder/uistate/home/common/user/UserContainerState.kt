package com.goforer.phogal.presentation.stateholder.uistate.home.common.user

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import com.goforer.phogal.presentation.stateholder.uistate.BaseUiState
import com.goforer.phogal.presentation.stateholder.uistate.rememberBaseUiState

@Stable
class UserContainerState(
    val baseUiState: BaseUiState,
    val userState: MutableState<String>,
    val profileSizeState: MutableState<Double>,
    val colorsState: MutableState<List<Color>>,
    val visibleViewButtonState: MutableState<Boolean>,
    val fromItemState: MutableState<Boolean>,
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberUserContainerState(
    baseUiState: BaseUiState = rememberBaseUiState(),
    userState: MutableState<String> = rememberSaveable { mutableStateOf("") },
    profileSizeState: MutableState<Double> = rememberSaveable { mutableDoubleStateOf(0.0) },
    colorsState: MutableState<List<Color>> = rememberSaveable { mutableStateOf(listOf(Color.Transparent, Color.Transparent, Color.Transparent, Color.Transparent, Color.Transparent)) },
    visibleViewButtonState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    fromItemState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
): UserContainerState = remember(
    profileSizeState, colorsState, visibleViewButtonState) {
    UserContainerState(
        baseUiState = baseUiState,
        userState = userState,
        profileSizeState = profileSizeState,
        colorsState = colorsState,
        visibleViewButtonState = visibleViewButtonState,
        fromItemState = fromItemState
    )
}