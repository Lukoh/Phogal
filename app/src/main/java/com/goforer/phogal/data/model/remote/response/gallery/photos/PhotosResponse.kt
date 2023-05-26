package com.goforer.phogal.data.model.remote.response.gallery.photos

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import com.goforer.phogal.data.model.remote.response.gallery.common.Photo
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotosResponse(
    val results: MutableList<Photo>,
    val total: Int,
    val total_pages: Int
) : BaseModel(), Parcelable