package com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class TagsPreviewUiState(
    val title: String = "",
    val type: String = ""
) : BaseModel(), Parcelable