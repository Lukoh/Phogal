package com.goforer.phogal.data.model.remote.response.gallery.common

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileImageUiState(
    val large: String,
    val medium: String,
    val small: String
) : BaseModel(), Parcelable