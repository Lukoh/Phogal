package com.goforer.phogal.data.model.remote.response.gallery.common

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class SponsorUiState(
    val accepted_tos: Boolean = false,
    val bio: String = "",
    val first_name: String = "",
    val for_hire: Boolean = false,
    val id: String = "",
    val instagram_username: String = "",
    val last_name: String? = null,
    val links: LinksXUiState = LinksXUiState(),
    val location: String = "",
    val name: String = "",
    val portfolio_url: String = "",
    val profile_image: ProfileImageUiState = ProfileImageUiState(),
    val social: SocialUiState = SocialUiState(),
    val total_collections: Int = 0,
    val total_likes: Int = 0,
    val total_photos: Int = 0,
    val twitter_username: String = "",
    val updated_at: String = "",
    val username: String = ""
) : BaseModel(), Parcelable