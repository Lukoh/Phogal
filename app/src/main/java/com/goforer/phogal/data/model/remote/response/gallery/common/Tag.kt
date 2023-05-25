package com.goforer.phogal.data.model.remote.response.gallery.common

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tag(
    val source: Source,
    val title: String,
    val type: String
) : BaseModel(), Parcelable