package com.goforer.phogal.data.model.remote.response.gallery.photos

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Links(
    val download: String,
    val download_location: String,
    val html: String,
    val self: String
) : BaseModel(), Parcelable