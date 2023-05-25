package com.goforer.phogal.data.repository.gallery.photo

import com.goforer.phogal.BuildConfig
import com.goforer.phogal.data.mediator.DataMediator
import com.goforer.phogal.data.model.remote.response.gallery.photo.Picture
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.data.network.response.Resource
import com.goforer.phogal.data.repository.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPictureRepository
@Inject
constructor() : Repository<Resource>() {
    override fun trigger(replyCount: Int, params: Params) =
        object : DataMediator<Picture>() {
            override fun load() = restAPI.getPhoto(BuildConfig.clientId, params.args[0] as String)
        }.asFlow
}