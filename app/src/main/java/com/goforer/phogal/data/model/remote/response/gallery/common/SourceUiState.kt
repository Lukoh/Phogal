package com.goforer.phogal.data.model.remote.response.gallery.common

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class SourceUiState(
    val ancestry: AncestryUiState? = null,
    val cover_photo: CoverPhotoUiState? = null,
    val description: String? = null,
    val meta_description: String? = null,
    val meta_title: String? = null,
    val subtitle: String? = null,
    val title: String? = null
) : BaseModel(), Parcelable