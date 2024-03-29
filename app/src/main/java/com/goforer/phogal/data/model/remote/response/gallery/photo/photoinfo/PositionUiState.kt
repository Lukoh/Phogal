package com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class PositionUiState(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) : BaseModel(), Parcelable