package com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import com.goforer.phogal.data.model.remote.response.gallery.common.LinksUiState
import com.goforer.phogal.data.model.remote.response.gallery.common.SponsorshipUiState
import com.goforer.phogal.data.model.remote.response.gallery.common.TagUiState
import com.goforer.phogal.data.model.remote.response.gallery.common.UrlsUiState
import com.goforer.phogal.data.model.remote.response.gallery.common.UserUiState
import kotlinx.parcelize.Parcelize

@Parcelize
data class PictureUiState(
    val alt_description: String?,
    val blur_hash: String,
    val color: String,
    val created_at: String,
    val description: String? = null,
    val downloads: Long,
    val exif: ExifUiState?,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Long,
    val links: LinksUiState,
    val location: LocationUiState?,
    val meta: MetaUiState,
    val promoted_at: String,
    val public_domain: Boolean,
    val related_collections: RelatedCollectionsUiState,
    val slug: String,
    val sponsorship: SponsorshipUiState?,
    val tags: List<TagUiState>?,
    val tags_preview: List<TagsPreviewUiState>?,
    val updated_at: String,
    val urls: UrlsUiState,
    val user: UserUiState,
    val views: Long,
    val width: Int,
    var bookmarked: Boolean
) : BaseModel(), Parcelable