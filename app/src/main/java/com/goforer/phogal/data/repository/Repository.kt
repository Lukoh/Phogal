package com.goforer.phogal.data.repository

import com.goforer.base.storage.LocalStorage
import com.goforer.phogal.data.network.NetworkErrorHandler
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.data.network.api.RestAPI
import kotlinx.coroutines.flow.SharedFlow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
abstract class Repository<Resource> {
    @Inject
    lateinit var restAPI: RestAPI

    @Inject
    lateinit var networkErrorHandler: NetworkErrorHandler

    @Inject
    lateinit var localStorage: LocalStorage

    lateinit var value: SharedFlow<Resource>

    companion object {
        internal const val ITEM_COUNT = 30
        internal const val FIRST_PAGE = 1

        internal var replyCount = 0
    }

    open fun trigger(replyCount: Int, params: Params) {
        Timber.d("trigger")
    }

    open fun invalidatePagingSource() {
        Timber.d("invalidatePagingSource")
    }

    protected fun handleNetworkError(errorMessage: String) {
        networkErrorHandler.handleError(errorMessage)
    }
}