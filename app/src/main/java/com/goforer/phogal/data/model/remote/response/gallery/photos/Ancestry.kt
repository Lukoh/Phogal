package com.goforer.phogal.data.model.remote.response.gallery.photos

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ancestry(
    val category: Category,
    val subcategory: Subcategory,
    val type: Type
) : BaseModel(), Parcelable