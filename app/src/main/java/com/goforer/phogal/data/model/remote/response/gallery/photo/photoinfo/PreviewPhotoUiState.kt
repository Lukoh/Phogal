package com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import com.goforer.phogal.data.model.remote.response.gallery.common.UrlsUiState
import kotlinx.parcelize.Parcelize

@Parcelize
data class PreviewPhotoUiState(
    val blur_hash: String = "",
    val created_at: String = "",
    val id: String = "",
    val slug: String = "",
    val updated_at: String = "",
    val urls: UrlsUiState = UrlsUiState()
) : BaseModel(), Parcelable