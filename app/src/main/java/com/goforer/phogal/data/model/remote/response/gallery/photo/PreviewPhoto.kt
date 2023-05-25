package com.goforer.phogal.data.model.remote.response.gallery.photo

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import com.goforer.phogal.data.model.remote.response.gallery.common.Urls
import kotlinx.parcelize.Parcelize

@Parcelize
data class PreviewPhoto(
    val blur_hash: String,
    val created_at: String,
    val id: String,
    val slug: String,
    val updated_at: String,
    val urls: Urls
) : BaseModel(), Parcelable