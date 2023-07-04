package com.goforer.phogal.data.repository.common.user.photos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.goforer.phogal.data.model.remote.response.gallery.common.Photo
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.repository.Repository
import com.goforer.phogal.data.repository.BasePagingSource
import com.goforer.phogal.data.repository.common.user.photos.paging.GetUserPhotosPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserPhotosRepository
@Inject
constructor() : Repository<PagingData<Photo>>() {
    private lateinit var pagingSource: GetUserPhotosPagingSource

    override fun trigger(replyCount: Int, params: Params): Flow<PagingData<Photo>> {
        Repository.replyCount = replyCount
        return Pager(
            config = PagingConfig(
                pageSize = params.args[1] as Int,
                prefetchDistance = ITEM_COUNT - 5,
                initialLoadSize = ITEM_COUNT
            ),
        ) {
            pagingSource = GetUserPhotosPagingSource()
            BasePagingSource.pageSize = params.args[1] as Int
            pagingSource.setPagingParam(restAPI, params)
            pagingSource
        }.flow
    }

    override fun invalidatePagingSource() {
        if (::pagingSource.isInitialized)
            pagingSource.invalidate()
    }
}