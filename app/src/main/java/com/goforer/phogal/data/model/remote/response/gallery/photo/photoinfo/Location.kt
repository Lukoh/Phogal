package com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val city: String,
    val country: String,
    val name: String?,
    val position: Position
) : BaseModel(), Parcelable