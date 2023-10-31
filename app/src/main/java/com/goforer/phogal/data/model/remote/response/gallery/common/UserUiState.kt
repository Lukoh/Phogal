package com.goforer.phogal.data.model.remote.response.gallery.common

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserUiState(
    val accepted_tos: Boolean = false,
    val bio: String? = null,
    val first_name: String = "",
    val for_hire: Boolean = false,
    val id: String = "",
    val instagram_username: String? = null,
    val last_name: String? = null,
    val links: LinksXUiState = LinksXUiState(),
    val location: String? = null,
    val name: String = "",
    val portfolio_url: String? = null,
    val profile_image: ProfileImageUiState = ProfileImageUiState(),
    val social: SocialUiState = SocialUiState(),
    val total_collections: Int = 0,
    val total_likes: Int = 0,
    val total_photos: Int = 0,
    val twitter_username: String? = null,
    val updated_at: String = "",
    val username: String = ""
) : BaseModel(), Parcelable {
    override fun toString(): String = Gson().toJson(this)
}