package com.goforer.phogal.data.model.remote.response.gallery.common

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class SponsorshipUiState(
    val impression_urls: List<String>? = null,
    val sponsor: SponsorUiState,
    val tagline: String,
    val tagline_url: String? = null
) : BaseModel(), Parcelable