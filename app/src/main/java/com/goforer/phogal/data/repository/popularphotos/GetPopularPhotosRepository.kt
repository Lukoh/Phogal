package com.goforer.phogal.data.repository.popularphotos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.model.remote.response.gallery.common.Photo
import com.goforer.phogal.data.repository.Repository
import com.goforer.phogal.data.repository.BasePagingSource
import com.goforer.phogal.data.repository.popularphotos.paging.GetPopularPhotosPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPopularPhotosRepository
@Inject
constructor() : Repository<PagingData<Photo>>() {
    private lateinit var pagingSource: GetPopularPhotosPagingSource
    override fun trigger(replyCount: Int, params: Params): Flow<PagingData<Photo>> {
        Repository.replyCount = replyCount
        return Pager(
            config =  PagingConfig(
                pageSize = params.args[2] as Int,
                prefetchDistance = ITEM_COUNT - 5,
                initialLoadSize = ITEM_COUNT
            )
        ) {
            pagingSource =  GetPopularPhotosPagingSource()
            BasePagingSource.pageSize = params.args[2] as Int
            pagingSource.setPagingParam(restAPI, params)
            pagingSource
        }.flow
    }

    override fun invalidatePagingSource() {
        pagingSource.invalidate()
    }
}