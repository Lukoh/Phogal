package com.goforer.phogal.presentation.stateholder.uistate.home.gallery.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.goforer.phogal.presentation.stateholder.uistate.BaseUiState
import com.goforer.phogal.presentation.stateholder.uistate.rememberBaseUiState

@Stable
class UserContainerState(
    val baseUiState: BaseUiState,
    val profileSize: MutableState<Dp>,
    val firstTextColor: MutableState<Color>,
    val secondTextColor: MutableState<Color>,
    val backgroundColor: MutableState<Color>,
    val visibleViewPhotosButton: MutableState<Boolean>,
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberUserContainerState(
    baseUiState: BaseUiState = rememberBaseUiState(),
    profileSize: MutableState<Dp> = rememberSaveable { mutableStateOf(0.dp) },
    firstTextColor: MutableState<Color> = rememberSaveable { mutableStateOf(Color.Transparent) },
    secondTextColor: MutableState<Color> = rememberSaveable { mutableStateOf(Color.Transparent) },
    backgroundColor: MutableState<Color> = rememberSaveable { mutableStateOf(Color.Transparent) },
    visibleViewPhotosButton: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
): UserContainerState = remember(
    baseUiState, profileSize, firstTextColor, secondTextColor, backgroundColor, visibleViewPhotosButton) {
    UserContainerState(
        baseUiState = baseUiState,
        profileSize = profileSize,
        firstTextColor = firstTextColor,
        secondTextColor = secondTextColor,
        backgroundColor = backgroundColor,
        visibleViewPhotosButton = visibleViewPhotosButton
    )
}