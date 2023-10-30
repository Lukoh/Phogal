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
    val cover_photo: CoverPhotoUiState,
    val curated: Boolean,
    val description: String,
    val featured: Boolean,
    val id: String,
    val last_collected_at: String,
    val links: LinksXUiState,
    val preview_photos: List<PreviewPhotoUiState>,
    val private: Boolean,
    val published_at: String,
    val share_key: String,
    val tags: List<TagUiState>,
    val title: String,
    val total_photos: Long,
    val updated_at: String,
    val user: UserUiState
) : BaseModel(), Parcelable