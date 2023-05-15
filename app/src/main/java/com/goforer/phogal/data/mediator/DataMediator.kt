package com.goforer.phogal.data.mediator

import androidx.annotation.MainThread
import com.goforer.phogal.data.network.response.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import timber.log.Timber

abstract class DataMediator<T> constructor(viewModelScope: CoroutineScope, replyCount: Int = 0) {
    private val resource by lazy {
        Resource()
    }

    internal val asSharedFlow = flow {
        load().map { apiResponse ->
            when (apiResponse) {
                is ApiSuccessResponse -> {
                    emit(resource.success(apiResponse.body))
                }

                is ApiEmptyResponse -> {
                    emit(resource.success(""))
                }

                is ApiErrorResponse -> {
                    Timber.e("Network-Error: ${apiResponse.errorMessage}")
                    emit(resource.error(apiResponse.errorMessage, apiResponse.statusCode))
                    onNetworkError(apiResponse.errorMessage, apiResponse.statusCode)
                }
            }
        }.onStart {
            emit(resource.loading(Status.LOADING))
        }.catch {
            emit(resource.error(it.message, 400))
        }
    }.shareIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        replay = replyCount
    )

    protected open suspend fun onNetworkError(errorMessage: String, errorCode: Int) {
        Timber.d("errorMessage")
    }

    @MainThread
    protected abstract fun load(): Flow<ApiResponse<T>>
}