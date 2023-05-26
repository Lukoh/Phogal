package com.goforer.phogal.data.model.local.error

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ErrorThrowable(
    val code: Int,
    val message: String
 ) : BaseModel(), Parcelable
