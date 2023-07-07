package com.goforer.phogal.data.model.remote.response.gallery.common

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    val alt_description: String? = null,
    val blur_hash: String,
    val color: String,
    val created_at: String,
    val description: String,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val links: Links,
    val meta_description: String? = null,
    val promoted_at: String,
    val slug: String,
    val sponsorship: Sponsorship?,
    val tags: List<Tag>?,
    val updated_at: String,
    val urls: Urls,
    val user: User,
    val width: Int,
    var alreadySearched: Boolean = false
) : BaseModel(), Parcelable