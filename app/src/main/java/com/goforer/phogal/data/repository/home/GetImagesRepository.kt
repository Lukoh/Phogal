package com.goforer.phogal.data.repository.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.goforer.phogal.data.mediator.PagingDataMediator
import com.goforer.phogal.data.model.remote.response.photos.Document
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.data.network.response.Resource
import com.goforer.phogal.data.repository.Repository
import com.goforer.phogal.data.repository.paging.source.BasePagingSource
import com.goforer.phogal.data.repository.paging.source.home.GetImagesPagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetImagesRepository
@Inject
constructor() : Repository() {
    @Inject
    lateinit var pagingSource: GetImagesPagingSource

    override fun trigger(viewModelScope: CoroutineScope, replyCount: Int, params: Params): SharedFlow<Resource> {
        Repository.replyCount = replyCount
        return object : PagingDataMediator<PagingData<Document>>(viewModelScope, replyCount) {
            override fun load(): Flow<PagingData<Document>> = Pager(
                config = PagingConfig(
                    pageSize = (params.args[2] as Int).times(3),
                    prefetchDistance = ITEM_COUNT.times(3) - 5,
                    initialLoadSize = ITEM_COUNT.times(3)
                )
            ) {
                BasePagingSource.pageSize = (params.args[2] as Int).times(3)
                pagingSource.setPagingParam(params)
                pagingSource

                /*
                pagingSource.setPagingParam(query, value)
                pagingSource

                 */
            }.flow.cachedIn(viewModelScope)
        }.asSharedFlow
    }

    override fun invalidatePagingSource() {
        pagingSource.invalidate()
    }
}