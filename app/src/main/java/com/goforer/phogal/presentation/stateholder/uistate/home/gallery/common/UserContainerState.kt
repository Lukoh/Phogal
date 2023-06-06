package com.goforer.phogal.presentation.stateholder.uistate.home.gallery.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color

@Stable
class UserContainerState(
    val profileSize: MutableState<Double>,
    val colors: List<Color>,
    val visibleViewPhotosButton: MutableState<Boolean>,
)

@Composable
fun rememberUserContainerState(
    profileSize: MutableState<Double> = rememberSaveable { mutableDoubleStateOf(0.0) },
    colors: List<Color> = rememberSaveable { listOf(Color.Transparent, Color.Transparent, Color.Transparent, Color.Transparent, Color.Transparent) },
    visibleViewPhotosButton: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
): UserContainerState = remember(
    profileSize, colors, visibleViewPhotosButton) {
    UserContainerState(
        profileSize = profileSize,
        colors = colors,
        visibleViewPhotosButton = visibleViewPhotosButton
    )
}