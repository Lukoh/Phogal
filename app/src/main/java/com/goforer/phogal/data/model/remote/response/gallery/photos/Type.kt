package com.goforer.phogal.data.model.remote.response.gallery.photos

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Type(
    val pretty_slug: String,
    val slug: String
) : BaseModel(), Parcelable