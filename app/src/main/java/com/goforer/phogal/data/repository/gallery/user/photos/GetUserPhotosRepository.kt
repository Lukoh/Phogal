package com.goforer.phogal.data.repository.gallery.user.photos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.goforer.phogal.data.model.remote.response.gallery.common.Photo
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.repository.Repository
import com.goforer.phogal.data.repository.paging.source.BasePagingSource
import com.goforer.phogal.data.repository.paging.source.user.GetUserPhotosPagingSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserPhotosRepository
@Inject
constructor() : Repository<PagingData<Photo>>() {
    @Inject
    lateinit var pagingSource: GetUserPhotosPagingSource

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
        }.flow.mapLatest {
            it
        }
    }

    override fun invalidatePagingSource() {
        pagingSource.invalidate()
    }
}