package com.goforer.phogal.data.model.response

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImagesResponse(
    var documents: MutableList<Document>,
    val meta: Meta
) : BaseModel(), Parcelable