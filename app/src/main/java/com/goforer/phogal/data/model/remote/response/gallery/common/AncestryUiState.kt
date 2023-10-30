package com.goforer.phogal.data.model.remote.response.gallery.common

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import com.goforer.phogal.data.model.remote.response.gallery.photos.CategoryUiState
import com.goforer.phogal.data.model.remote.response.gallery.photos.SubcategoryUiState
import com.goforer.phogal.data.model.remote.response.gallery.photos.TypeUiState
import kotlinx.parcelize.Parcelize

@Parcelize
data class AncestryUiState(
    val category: CategoryUiState?,
    val subcategory: SubcategoryUiState? = null,
    val type: TypeUiState?
) : BaseModel(), Parcelable