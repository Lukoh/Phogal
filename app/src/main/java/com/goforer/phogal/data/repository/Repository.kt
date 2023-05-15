package com.goforer.phogal.data.repository

import com.goforer.base.storage.LocalStorage
import com.goforer.phogal.data.network.NetworkErrorHandler
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.data.network.api.RestAPI
import com.goforer.phogal.data.network.response.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
abstract class Repository constructor() {
    @Inject
    lateinit var restAPI: RestAPI

    @Inject
    lateinit var networkErrorHandler: NetworkErrorHandler

    @Inject
    lateinit var localStorage: LocalStorage

    @Inject
    lateinit var resource: Resource

    /*
    var value: SharedFlow<Resource> = flow {
        emit(resource.loading(Status.LOADING))
    }.shareIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        replay = 2
    )

     */

    companion object {
        internal const val ITEM_COUNT = 30
        internal const val FIRST_PAGE = 1

        internal var replyCount = 0
    }

    abstract fun trigger(viewModelScope: CoroutineScope, replyCount: Int, params: Params): SharedFlow<Resource>

    open fun invalidatePagingSource() {
        Timber.d("invalidatePagingSource")
    }

    protected fun handleNetworkError(errorMessage: String) {
        networkErrorHandler.handleError(errorMessage)
    }
}