package com.goforer.phogal.presentation.stateholder.uistate

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalComposeUiApi::class)
@Stable
class BaseUiState<T>(
    val resourceStateFlow: StateFlow<T>,
    val context: Context?,
    val scope: CoroutineScope?,
    val lifecycle: Lifecycle?,
    val keyboardController: SoftwareKeyboardController?,
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun <T> rememberBaseUiState(
    resourceStateFlow: StateFlow<T>,
    context: Context = LocalContext.current,
    scope: CoroutineScope = rememberCoroutineScope(),
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
): BaseUiState<T> = remember(
    resourceStateFlow, context, scope, lifecycle, keyboardController) {
    BaseUiState(
        resourceStateFlow = resourceStateFlow,
        context = context,
        scope = scope,
        lifecycle = lifecycle,
        keyboardController = keyboardController,
    )
}