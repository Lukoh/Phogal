package com.goforer.phogal.data.model.remote.response.gallery.photo.like

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import com.goforer.phogal.data.model.remote.response.gallery.common.Photo
import com.goforer.phogal.data.model.remote.response.gallery.common.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class LikeResponse(
    val photo: Photo,
    val user: User
) : BaseModel(), Parcelable