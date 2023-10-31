package com.goforer.phogal.data.model.remote.response.gallery.common

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoverPhotoUiState(
    val alt_description: String = "",
    val blur_hash: String = "",
    val color: String = "",
    val created_at: String = "",
    val description: String = "",
    val height: Int = 0,
    val id: String = "",
    val liked_by_user: Boolean = false,
    val likes: Int = 0,
    val links: LinksXUiState? = null,
    val plus: Boolean = false,
    val premium: Boolean = false,
    val promoted_at: String = "",
    val slug: String = "",
    val topic_submissions: TopicSubmissionsUiState? = null,
    val updated_at: String = "",
    val urls: UrlsUiState = UrlsUiState(),
    val user: UserUiState = UserUiState(),
    val width: Int = 0
) : BaseModel(), Parcelable