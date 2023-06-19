package com.goforer.phogal.presentation.stateholder.uistate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import com.goforer.phogal.data.model.remote.state.ResourceState
import com.goforer.phogal.data.datasource.network.response.Status
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <T> rememberResourceState(resourceStateFlow: StateFlow<T>): ResourceState<StateFlow<T>> {
    return produceState(initialValue = ResourceState(status = Status.LOADING)) {
        value = ResourceState(status = Status.SUCCESS, resourceStateFlow = resourceStateFlow)
    }.value
}