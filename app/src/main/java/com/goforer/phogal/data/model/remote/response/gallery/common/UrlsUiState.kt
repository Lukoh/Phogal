package com.goforer.phogal.data.model.remote.response.gallery.common

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class UrlsUiState(
    val full: String = "",
    val raw: String = "",
    val regular: String = "",
    val small: String = "",
    val small_s3: String? = null,
    val thumb: String = ""
) : BaseModel(), Parcelable