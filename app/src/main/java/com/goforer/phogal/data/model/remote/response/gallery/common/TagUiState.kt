package com.goforer.phogal.data.model.remote.response.gallery.common

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class TagUiState(
    val source: SourceUiState? = null,
    val title: String? = null,
    val type: String? = null
) : BaseModel(), Parcelable