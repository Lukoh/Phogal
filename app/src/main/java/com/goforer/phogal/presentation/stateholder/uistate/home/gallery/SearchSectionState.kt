package com.goforer.phogal.presentation.stateholder.uistate.home.gallery

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import com.goforer.phogal.presentation.stateholder.uistate.EditableInputState
import com.goforer.phogal.presentation.stateholder.uistate.rememberEditableInputState

@Stable
class SearchSectionState(
    val editableInputState: EditableInputState,
    val interactionSource: MutableInteractionSource,
    val wordChangedState: MutableState<Boolean>,
    val enabledState: MutableState<Boolean>
)

@Composable
fun rememberSearchSectionState(
    editableInputState: EditableInputState = rememberEditableInputState(hint = "Search"),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    wordChangedState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    enabledState: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
): SearchSectionState = remember {
    SearchSectionState(
        editableInputState = editableInputState,
        interactionSource = interactionSource,
        wordChangedState = wordChangedState,
        enabledState = enabledState
    )
}