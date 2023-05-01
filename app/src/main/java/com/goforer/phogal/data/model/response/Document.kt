package com.goforer.phogal.data.model.response

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Document(
    val author: String? = null,
    val collection: String? = null,
    val datetime: String,
    val display_sitename: String? = null,
    val doc_url: String? = null,
    val height: Int? = null,
    val image_url: String? = null,
    val play_time: Int,
    val thumbnail: String? = null,
    val thumbnail_url: String? = null,
    val title: String? = null,
    val url: String? = null,
    val width: Int? = null,
    var liked: Boolean = false,
    var deleted: Boolean = false
) : BaseModel(), Parcelable