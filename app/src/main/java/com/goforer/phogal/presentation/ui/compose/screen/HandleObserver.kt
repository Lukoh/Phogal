package com.goforer.phogal.presentation.ui.compose.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun HandleObserver(
    lifecycle: Lifecycle,
    onStart: () -> Unit,
    onStop: () -> Unit
) {
    DisposableEffect(lifecycle) {
        // Create an observer that triggers our remembered callbacks
        // for doing anything
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                onStart()
            } else if (event == Lifecycle.Event.ON_STOP) {
                onStop()
            }
        }

        // Add the observer to the lifecycle
        lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}