package com.goforer.phogal.presentation.stateholder.uistate.photos

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.goforer.phogal.presentation.stateholder.uistate.EditableInputState
import com.goforer.phogal.presentation.stateholder.uistate.rememberEditableInputState

@Stable
class SearchSectionState(
    val editableInputState: EditableInputState,
    val interactionSource: MutableInteractionSource,
)

@Composable
fun rememberSearchSectionState(
    editableInputState: EditableInputState = rememberEditableInputState(hint = "Search"),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
): SearchSectionState = remember {
    SearchSectionState(editableInputState = editableInputState, interactionSource = interactionSource)
}