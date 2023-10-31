package com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import com.goforer.phogal.data.model.remote.response.gallery.common.CoverPhotoUiState
import com.goforer.phogal.data.model.remote.response.gallery.common.LinksXUiState
import com.goforer.phogal.data.model.remote.response.gallery.common.TagUiState
import com.goforer.phogal.data.model.remote.response.gallery.common.UserUiState
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultUiState(
    val cover_photo: CoverPhotoUiState = CoverPhotoUiState(),
    val curated: Boolean = false,
    val description: String = "",
    val featured: Boolean = false,
    val id: String = "",
    val last_collected_at: String = "",
    val links: LinksXUiState = LinksXUiState(),
    val preview_photos: List<PreviewPhotoUiState> = listOf(),
    val private: Boolean = false,
    val published_at: String = "",
    val share_key: String = "",
    val tags: List<TagUiState> = listOf(),
    val title: String = "",
    val total_photos: Long = 0L,
    val updated_at: String = "",
    val user: UserUiState = UserUiState()
) : BaseModel(), Parcelable