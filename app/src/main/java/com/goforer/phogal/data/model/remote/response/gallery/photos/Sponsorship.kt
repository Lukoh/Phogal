package com.goforer.phogal.data.model.remote.response.gallery.photos

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sponsorship(
    val sponsor: Sponsor,
    val tagline: String,
    val tagline_url: String
) : BaseModel(), Parcelable