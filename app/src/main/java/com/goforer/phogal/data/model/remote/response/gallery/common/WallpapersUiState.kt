package com.goforer.phogal.data.model.remote.response.gallery.common

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class WallpapersUiState(
    val approved_on: String? = null,
    val status: String? = null
) : BaseModel(), Parcelable