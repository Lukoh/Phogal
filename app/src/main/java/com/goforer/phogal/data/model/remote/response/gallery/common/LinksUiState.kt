package com.goforer.phogal.data.model.remote.response.gallery.common

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class LinksUiState(
    val download: String = "",
    val download_location: String? = null,
    val html: String = "",
    val self: String = ""
) : BaseModel(), Parcelable