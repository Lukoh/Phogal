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
    val alt_description: String? = null,
    val blur_hash: String = "",
    val color: String = "",
    val created_at: String = "",
    val description: String? = null,
    val downloads: Long = 0L,
    val exif: ExifUiState? = null,
    val height: Int = 0,
    val id: String = "",
    val liked_by_user: Boolean = false,
    val likes: Long = 0L,
    val links: LinksUiState = LinksUiState(),
    val location: LocationUiState? = null,
    val meta: MetaUiState = MetaUiState(),
    val promoted_at: String = "",
    val public_domain: Boolean = false,
    val related_collections: RelatedCollectionsUiState = RelatedCollectionsUiState(),
    val slug: String = "",
    val sponsorship: SponsorshipUiState? = null,
    val tags: List<TagUiState>? = null,
    val tags_preview: List<TagsPreviewUiState>? = null,
    val updated_at: String = "",
    val urls: UrlsUiState = UrlsUiState(),
    val user: UserUiState = UserUiState(),
    val views: Long = 0L,
    val width: Int = 0,
    var bookmarked: Boolean = false,
) : BaseModel(), Parcelable