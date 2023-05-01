package com.goforer.phogal.presentation.stateholder.uistate.photos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
class PhotoItemState(
    var heightDpState: MutableState<Dp>
)

@Composable
fun rememberPhotoItemState(
    heightDpState: MutableState<Dp> = remember { mutableStateOf(56.dp) },
): PhotoItemState = remember(heightDpState) {
    PhotoItemState(
        heightDpState = heightDpState
    )
}