package com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import com.goforer.phogal.data.model.remote.response.gallery.common.CoverPhoto
import com.goforer.phogal.data.model.remote.response.gallery.common.LinksX
import com.goforer.phogal.data.model.remote.response.gallery.common.Tag
import com.goforer.phogal.data.model.remote.response.gallery.common.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    val cover_photo: CoverPhoto,
    val curated: Boolean,
    val description: String,
    val featured: Boolean,
    val id: String,
    val last_collected_at: String,
    val links: LinksX,
    val preview_photos: List<PreviewPhoto>,
    val private: Boolean,
    val published_at: String,
    val share_key: String,
    val tags: List<Tag>,
    val title: String,
    val total_photos: Long,
    val updated_at: String,
    val user: User
) : BaseModel(), Parcelable