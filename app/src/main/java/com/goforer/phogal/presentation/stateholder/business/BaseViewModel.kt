package com.goforer.phogal.presentation.stateholder.business

import androidx.lifecycle.ViewModel
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.datasource.network.response.ApiEmptyResponse
import com.goforer.phogal.data.datasource.network.response.ApiErrorResponse
import com.goforer.phogal.data.datasource.network.response.ApiResponse
import com.goforer.phogal.data.datasource.network.response.ApiSuccessResponse
import com.goforer.phogal.data.datasource.network.response.Resource
import com.goforer.phogal.data.repository.PagingErrorMessage
import timber.log.Timber
import javax.inject.Singleton

@Singleton
abstract class BaseViewModel<T> : ViewModel() {
    val resource by lazy {
        Resource()
    }

    protected var errorMessage = PagingErrorMessage.PAGING_NORMAL
    protected var errorCode = 200

    open fun trigger(replyCount: Int = 1, params: Params = Params()) {
        Timber.d("Triggered Params")
    }

    open fun invalidate() {
        Timber.d("Invalidate")
    }

    protected fun handleResponse(response: ApiResponse<T>): Resource {
        return when (response) {
            is ApiSuccessResponse -> {
                resource.success(response.body)
            }

            is ApiEmptyResponse -> {
                resource.success("")
            }

            is ApiErrorResponse -> {
                resource.error(response.errorMessage, response.statusCode)
            }
        }
    }
}