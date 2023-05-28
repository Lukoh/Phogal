package com.goforer.phogal.data.model.remote.response.gallery.photo

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import com.goforer.phogal.data.model.remote.response.gallery.common.Links
import com.goforer.phogal.data.model.remote.response.gallery.common.Sponsorship
import com.goforer.phogal.data.model.remote.response.gallery.common.Tag
import com.goforer.phogal.data.model.remote.response.gallery.common.Urls
import com.goforer.phogal.data.model.remote.response.gallery.common.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class Picture(
    val alt_description: String?,
    val blur_hash: String,
    val color: String,
    val created_at: String,
    val description: String? = null,
    val downloads: Int,
    val exif: Exif?,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val links: Links,
    val location: Location?,
    val meta: Meta,
    val promoted_at: String,
    val public_domain: Boolean,
    val related_collections: RelatedCollections,
    val slug: String,
    val sponsorship: Sponsorship,
    val tags: List<Tag>,
    val tags_preview: List<TagsPreview>,
    val updated_at: String,
    val urls: Urls,
    val user: User,
    val views: Int,
    val width: Int
) : BaseModel(), Parcelable