package com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExifUiState(
    val aperture: String? = null,
    val exposure_time: String? = null,
    val focal_length: String? = null,
    val iso: Int? = null,
    val make: String? = null,
    val model: String? = null,
    val name: String? = null
) : BaseModel(), Parcelable