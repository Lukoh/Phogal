package com.goforer.phogal.data.repository.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.goforer.phogal.data.model.remote.response.photos.Document
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.data.repository.Repository
import com.goforer.phogal.data.repository.paging.source.BasePagingSource
import com.goforer.phogal.data.repository.paging.source.home.GetImagesPagingSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetImagesRepository
@Inject
constructor() : Repository<PagingData<Document>>() {
    @Inject
    lateinit var pagingSource: GetImagesPagingSource

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun trigger(replyCount: Int, params: Params): Flow<PagingData<Document>> {
        Repository.replyCount = replyCount
        return Pager(
            config = PagingConfig(
                pageSize = params.args[2] as Int,
                prefetchDistance = ITEM_COUNT - 5,
                initialLoadSize = ITEM_COUNT
            ),
        ) {
            BasePagingSource.pageSize = params.args[2] as Int
            pagingSource.setPagingParam(params)
            pagingSource
        }.flow.mapLatest {
            it
        }
    }

    override fun invalidatePagingSource() {
        pagingSource.invalidate()
    }
}