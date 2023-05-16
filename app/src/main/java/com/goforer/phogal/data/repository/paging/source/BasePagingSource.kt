package com.goforer.phogal.data.repository.paging.source

import androidx.paging.PagingSource
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.data.network.api.RestAPI
import com.goforer.phogal.data.network.response.ApiEmptyResponse
import com.goforer.phogal.data.network.response.ApiErrorResponse
import com.goforer.phogal.data.network.response.ApiResponse
import com.goforer.phogal.data.network.response.ApiSuccessResponse
import com.goforer.phogal.data.repository.paging.PagingErrorMessage.PAGING_EMPTY
import com.goforer.phogal.data.repository.paging.PagingErrorMessage.PAGING_NORMAL
import javax.inject.Inject

abstract class BasePagingSource<Key : Any, Response : Any, Value : Any> : PagingSource<Key, Value>() {
    protected lateinit var pagingList: MutableList<Value>

    protected var errorMessage = PAGING_NORMAL

    protected lateinit var params: Params

    protected var next = 0

    @Inject
    lateinit var restAPI: RestAPI

    companion object {
        internal var pageSize = 0
    }

    protected suspend fun request() {
        requestAPI(this.params)
    }

    protected fun handleKey(params: LoadParams<Int>, next: Int): Int {
        return if (next == 0)
            0
        else {
            this.next = next
            params.key?.plus(1) ?: 1
        }
    }

    protected fun handleResponse(response: ApiResponse<Response>): Response? {
        return when (response) {
            is ApiSuccessResponse -> {
                response.body
            }

            is ApiEmptyResponse -> {
                errorMessage = PAGING_EMPTY
                null
            }

            is ApiErrorResponse -> {
                errorMessage = response.errorMessage
                null
            }
        }
    }

    protected open suspend fun requestAPI(params: Params) {}

    internal fun setPagingParam(params: Params) {
        this.params = params
    }
}