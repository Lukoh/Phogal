package com.goforer.phogal.data.repository.gallery

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.model.remote.response.gallery.common.PhotoUiState
import com.goforer.phogal.data.repository.Repository
import com.goforer.phogal.data.repository.BasePagingSource
import com.goforer.phogal.data.repository.gallery.paging.GetPhotosPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPhotosRepository
@Inject
constructor() : Repository<PagingData<PhotoUiState>>() {
    private lateinit var pagingSource: GetPhotosPagingSource

    override fun trigger(replyCount: Int, params: Params): Flow<PagingData<PhotoUiState>> {
        Repository.replyCount = replyCount
        return Pager(
            config = PagingConfig(
                pageSize = if (params.args.size > 1 && params.args[1] != null && params.args[1] is Int)
                    params.args[1] as Int
                else
                    0,
                prefetchDistance = ITEM_COUNT - 5,
                initialLoadSize = ITEM_COUNT
            ),
        ) {
            pagingSource = GetPhotosPagingSource()
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