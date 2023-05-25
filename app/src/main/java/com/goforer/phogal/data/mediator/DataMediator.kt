package com.goforer.phogal.data.mediator

import androidx.annotation.MainThread
import com.goforer.phogal.data.network.response.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import timber.log.Timber

abstract class DataMediator<T> {
    private val resource by lazy {
        Resource()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    internal val asFlow = flow {
        emit(resource.loading(Status.LOADING))
        load().collectLatest { apiResponse ->
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
        }
    }

    protected open suspend fun onNetworkError(errorMessage: String, errorCode: Int) {
        Timber.d("errorMessage")
    }

    @MainThread
    protected abstract fun load(): Flow<ApiResponse<T>>
}