package com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class RelatedCollections(
    val results: List<Result>,
    val total: Int,
    val type: String
) : BaseModel(), Parcelable