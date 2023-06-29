package com.goforer.phogal.data.repository

import com.goforer.phogal.data.datasource.local.LocalDataSource
import com.goforer.phogal.data.datasource.network.NetworkErrorHandler
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.datasource.network.api.RestAPI
import com.goforer.phogal.data.datasource.network.response.Resource
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
abstract class Repository<T> {
    @Inject
    lateinit var restAPI: RestAPI

    @Inject
    lateinit var networkErrorHandler: NetworkErrorHandler

    @Inject
    lateinit var localDataSource: LocalDataSource

    @Inject
    lateinit var resource: Resource

    companion object {
        internal const val ITEM_COUNT = 10
        internal const val FIRST_PAGE = 1

        internal const val POPULAR = "popular"

        internal var replyCount = 0
    }

    abstract fun trigger(replyCount: Int, params: Params): Flow<T>

    open fun invalidatePagingSource() {
        Timber.d("invalidatePagingSource")
    }

    protected fun handleNetworkError(errorMessage: String) {
        networkErrorHandler.handleError(errorMessage)
    }
}