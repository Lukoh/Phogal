package com.goforer.phogal.data.model.remote.response.gallery.common

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val accepted_tos: Boolean,
    val bio: String?,
    val first_name: String,
    val for_hire: Boolean,
    val id: String,
    val instagram_username: String?,
    val last_name: String?,
    val links: LinksX,
    val location: String?,
    val name: String,
    val portfolio_url: String?,
    val profile_image: ProfileImage,
    val social: Social,
    val total_collections: Int,
    val total_likes: Int,
    val total_photos: Int,
    val twitter_username: String?,
    val updated_at: String,
    val username: String
) : BaseModel(), Parcelable {
    override fun toString(): String = Gson().toJson(this)
}