package com.goforer.phogal.data.repository.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.goforer.phogal.data.mediator.PagingDataMediator
import com.goforer.phogal.data.model.response.Document
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.data.repository.Repository
import com.goforer.phogal.data.repository.paging.source.BasePagingSource
import com.goforer.phogal.data.repository.paging.source.home.GetImagesPagingSource
import com.goforer.phogal.di.module.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetImagesRepository
@Inject
constructor(
    @ApplicationScope private val externalScope: CoroutineScope
) : Repository(externalScope) {
    @Inject
    lateinit var pagingSource: GetImagesPagingSource

    override fun trigger(replyCount: Int, params: Params) {
        Repository.replyCount = replyCount
        value = object : PagingDataMediator<PagingData<Document>>(externalScope, replyCount) {
            override fun load(): Flow<PagingData<Document>> = Pager(
                config = PagingConfig(
                    pageSize = ITEM_COUNT.times(3),
                    prefetchDistance = ITEM_COUNT.times(3) - 5,
                    initialLoadSize = ITEM_COUNT.times(3)
                )
            ) {
                BasePagingSource.pageSize = ITEM_COUNT.times(3)
                pagingSource.setPagingParam(params)
                pagingSource

                /*
                pagingSource.setPagingParam(query, value)
                pagingSource

                 */
            }.flow.cachedIn(externalScope)
        }.asSharedFlow
    }

    override fun invalidatePagingSource() {
        pagingSource.invalidate()
    }
}