package com.goforer.phogal.data.model.remote.response.gallery.common

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class SourceUiState(
    val ancestry: AncestryUiState?,
    val cover_photo: CoverPhotoUiState?,
    val description: String?,
    val meta_description: String?,
    val meta_title: String?,
    val subtitle: String?,
    val title: String?
) : BaseModel(), Parcelable