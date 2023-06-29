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
    val profileSize: MutableState<Double>,
    val colors: List<Color>,
    val visibleViewPhotosButton: MutableState<Boolean>,
    val isFromItem: MutableState<Boolean>,
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberUserContainerState(
    baseUiState: BaseUiState = rememberBaseUiState(),
    profileSize: MutableState<Double> = rememberSaveable { mutableDoubleStateOf(0.0) },
    colors: List<Color> = rememberSaveable { listOf(Color.Transparent, Color.Transparent, Color.Transparent, Color.Transparent, Color.Transparent) },
    visibleViewPhotosButton: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    isFromItem: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
): UserContainerState = remember(
    profileSize, colors, visibleViewPhotosButton) {
    UserContainerState(
        baseUiState = baseUiState,
        profileSize = profileSize,
        colors = colors,
        visibleViewPhotosButton = visibleViewPhotosButton,
        isFromItem = isFromItem
    )
}