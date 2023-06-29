package com.goforer.phogal.data.repository.common.photo.like

import com.goforer.phogal.BuildConfig
import com.goforer.phogal.data.model.remote.response.gallery.photo.like.LikeResponse
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.datasource.network.response.ApiResponse
import com.goforer.phogal.data.repository.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostPictureLikeRepository
@Inject
constructor() : Repository<ApiResponse<LikeResponse>>() {
    override fun trigger(replyCount: Int, params: Params) = restAPI.postLike(params.args[0] as String, BuildConfig.clientId)
}