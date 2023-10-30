package com.goforer.phogal.data.repository.common.photo.info

import com.goforer.phogal.BuildConfig
import com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo.PictureUiState
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.datasource.network.response.ApiResponse
import com.goforer.phogal.data.repository.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPictureRepository
@Inject
constructor() : Repository<ApiResponse<PictureUiState>>() {
    override fun trigger(replyCount: Int, params: Params) = restAPI.getPhoto(params.args[0] as String, BuildConfig.clientId)
}