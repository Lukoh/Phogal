package com.goforer.phogal.data.repository.gallery.photo

import com.goforer.phogal.BuildConfig
import com.goforer.phogal.data.model.remote.response.gallery.photo.Picture
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.data.network.response.ApiResponse
import com.goforer.phogal.data.repository.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPictureRepository
@Inject
constructor() : Repository<ApiResponse<Picture>>() {
    override fun trigger(replyCount: Int, params: Params) = restAPI.getPhoto(params.args[0] as String, BuildConfig.clientId)
}