package com.goforer.phogal.data.repository.gallery

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.goforer.phogal.data.model.remote.response.gallery.common.Photo
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.repository.Repository
import com.goforer.phogal.data.repository.BasePagingSource
import com.goforer.phogal.data.repository.gallery.paging.GetPhotosPagingSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPhotosRepository
@Inject
constructor() : Repository<PagingData<Photo>>() {
    @Inject
    lateinit var pagingSource: GetPhotosPagingSource

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun trigger(replyCount: Int, params: Params): Flow<PagingData<Photo>> {
        Repository.replyCount = replyCount
        return Pager(
            config = PagingConfig(
                pageSize = params.args[1] as Int,
                prefetchDistance = ITEM_COUNT - 5,
                initialLoadSize = ITEM_COUNT
            ),
        ) {
            BasePagingSource.pageSize = params.args[1] as Int
            pagingSource.setPagingParam(params)
            pagingSource
        }.flow
    }

    override fun invalidatePagingSource() {
        pagingSource.invalidate()
    }
}