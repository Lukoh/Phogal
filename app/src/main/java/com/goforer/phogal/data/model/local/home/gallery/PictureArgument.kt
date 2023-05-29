package com.goforer.phogal.data.model.local.home.gallery

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class PictureArgument(
    val id: String,
    val visibleViewPhotosButton: Boolean
) : BaseModel(), Parcelable
