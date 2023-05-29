package com.goforer.phogal.data.model.local.home.gallery

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class NameArgument(
    val name: String,
    val firstName: String,
    val lastName: String,
    val username: String
) : BaseModel(), Parcelable
