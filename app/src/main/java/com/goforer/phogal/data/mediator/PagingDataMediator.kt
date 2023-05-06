package com.goforer.phogal.data.mediator

import androidx.annotation.MainThread
import com.goforer.phogal.data.network.response.Resource
import com.goforer.phogal.data.network.response.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

abstract class PagingDataMediator<Response> constructor(viewModelScope: CoroutineScope) {
    @Inject
    lateinit var resource: Resource

    internal val asSharedFlow = flow {
        emit(resource.loading(Status.LOADING))
        load().collect {
            emit(resource.success(it))
        }
    }.shareIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        replay = 2
    )

    @MainThread
    protected abstract fun load(): Flow<Response>
}