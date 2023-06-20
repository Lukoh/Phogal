package com.goforer.phogal.data.model.remote.response.gallery.common

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sponsorship(
    val impression_urls: List<String>? = null,
    val sponsor: Sponsor,
    val tagline: String,
    val tagline_url: String? = null
) : BaseModel(), Parcelable