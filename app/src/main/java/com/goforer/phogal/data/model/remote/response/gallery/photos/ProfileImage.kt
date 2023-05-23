package com.goforer.phogal.data.model.remote.response.gallery.photos

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileImage(
    val large: String,
    val medium: String,
    val small: String
) : BaseModel(), Parcelable