package com.goforer.phogal.data.model.local.home.gallery

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class WebViewArgument(
    val firstName: String,
    val url: String
) : BaseModel(), Parcelable
