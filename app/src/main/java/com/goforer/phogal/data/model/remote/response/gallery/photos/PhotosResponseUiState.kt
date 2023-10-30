package com.goforer.phogal.data.model.remote.response.gallery.photos

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import com.goforer.phogal.data.model.remote.response.gallery.common.PhotoUiState
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotosResponseUiState(
    val results: MutableList<PhotoUiState>,
    val total: Int,
    val total_pages: Int
) : BaseModel(), Parcelable