package com.goforer.phogal.data.model.remote.response.gallery.photo.like

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import com.goforer.phogal.data.model.remote.response.gallery.common.PhotoUiState
import com.goforer.phogal.data.model.remote.response.gallery.common.UserUiState
import kotlinx.parcelize.Parcelize

@Parcelize
data class LikeResponseUiState(
    val photo: PhotoUiState = PhotoUiState(),
    val user: UserUiState = UserUiState()
) : BaseModel(), Parcelable