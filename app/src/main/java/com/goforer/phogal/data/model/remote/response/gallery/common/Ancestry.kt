package com.goforer.phogal.data.model.remote.response.gallery.common

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import com.goforer.phogal.data.model.remote.response.gallery.photos.Category
import com.goforer.phogal.data.model.remote.response.gallery.photos.Subcategory
import com.goforer.phogal.data.model.remote.response.gallery.photos.Type
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ancestry(
    val category: Category,
    val subcategory: Subcategory? = null,
    val type: Type
) : BaseModel(), Parcelable