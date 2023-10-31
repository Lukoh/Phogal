package com.goforer.phogal.data.model.remote.response.gallery.common

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class LinksXUiState(
    val followers: String? = null,
    val following: String? = null,
    val html: String = "",
    val likes: String = "",
    val photos: String = "",
    val portfolio: String? = null,
    val related: String? = null,
    val self: String = ""
) : BaseModel(), Parcelable