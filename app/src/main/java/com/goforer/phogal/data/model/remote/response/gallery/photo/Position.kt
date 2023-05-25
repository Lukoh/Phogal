package com.goforer.phogal.data.model.remote.response.gallery.photo

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Position(
    val latitude: Double,
    val longitude: Double
) : BaseModel(), Parcelable