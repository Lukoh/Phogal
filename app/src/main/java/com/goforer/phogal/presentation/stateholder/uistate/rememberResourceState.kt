package com.goforer.phogal.presentation.stateholder.uistate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import com.goforer.phogal.data.model.state.ResourceState
import com.goforer.phogal.data.network.response.Status
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <T> rememberResourceState(resourceStateFlow: StateFlow<T>): ResourceState<StateFlow<T>> {
    return produceState(initialValue = ResourceState(status = Status.LOADING)) {
        // will be changed if the data come from Backend Server like below:
        /*
        value = ResourceState(status = baseUiState.uiState.value.status, data = baseUiState.uiState)
         */
        value = ResourceState(status = Status.SUCCESS, resourceStateFlow = resourceStateFlow)
    }.value
}