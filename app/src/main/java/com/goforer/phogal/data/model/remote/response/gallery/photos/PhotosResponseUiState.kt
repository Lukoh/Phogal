package com.goforer.phogal.data.model.remote.response.gallery.photos

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import com.goforer.phogal.data.model.remote.response.gallery.common.PhotoUiState
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotosResponseUiState(
    val results: MutableList<PhotoUiState> = listOf<PhotoUiState>().toMutableList(),
    val total: Int = 0,
    val total_pages: Int = 0
) : BaseModel(), Parcelable