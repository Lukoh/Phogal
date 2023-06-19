package com.goforer.phogal.presentation.stateholder.business

import androidx.lifecycle.ViewModel
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.data.network.response.ApiEmptyResponse
import com.goforer.phogal.data.network.response.ApiErrorResponse
import com.goforer.phogal.data.network.response.ApiResponse
import com.goforer.phogal.data.network.response.ApiSuccessResponse
import com.goforer.phogal.data.network.response.Resource
import com.goforer.phogal.data.repository.paging.PagingErrorMessage
import timber.log.Timber
import javax.inject.Singleton

@Singleton
abstract class BaseViewModel<T> : ViewModel() {
    val resource by lazy {
        Resource()
    }

    protected var errorMessage = PagingErrorMessage.PAGING_NORMAL
    protected var errorCode = 200

    open fun trigger(replyCount: Int, params: Params = Params()) {
        Timber.d("Triggered Params")
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