package com.goforer.phogal.data.repository

import androidx.paging.PagingSource
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.datasource.network.api.RestAPI
import com.goforer.phogal.data.datasource.network.response.ApiEmptyResponse
import com.goforer.phogal.data.datasource.network.response.ApiErrorResponse
import com.goforer.phogal.data.datasource.network.response.ApiResponse
import com.goforer.phogal.data.datasource.network.response.ApiSuccessResponse
import com.goforer.phogal.data.datasource.network.response.Resource
import timber.log.Timber

abstract class BasePagingSource<Key : Any, Response : Any, Value : Any> : PagingSource<Key, Value>() {
    protected lateinit var pagingList: MutableList<Value>

    protected val resource by lazy {
        Resource()
    }

    private lateinit var params: Params

    protected var next = 0

    lateinit var restAPI: RestAPI

    companion object {
        internal var pageSize = 0
    }

    protected suspend fun request(nextKey: Int) {
        requestAPI(this.params, nextKey)
    }

    protected fun handleKey(params: LoadParams<Int>, next: Int): Int {
        return if (next == 0)
            0
        else {
            this.next = next
            params.key?.plus(1) ?: 1
        }
    }

    protected fun handleResponse(response: ApiResponse<Response>): Resource {
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

    protected open suspend fun requestAPI(params: Params, nextKey: Int) {
        Timber.d("Called REST API")
    }

    internal fun setPagingParam(restAPI: RestAPI, params: Params) {
        this.restAPI = restAPI
        this.params = params
    }
}