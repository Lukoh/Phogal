package com.goforer.phogal.data.model.remote.response.gallery.common

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class SocialUiState(
    val instagram_username: String = "",
    val paypal_email: String? = null,
    val portfolio_url: String = "",
    val twitter_username: String = ""
) : BaseModel(), Parcelable