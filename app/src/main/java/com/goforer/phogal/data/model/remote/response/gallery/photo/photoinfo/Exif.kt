package com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exif(
    val aperture: String?,
    val exposure_time: String?,
    val focal_length: String?,
    val iso: Int?,
    val make: String?,
    val model: String?,
    val name: String?
) : BaseModel(), Parcelable