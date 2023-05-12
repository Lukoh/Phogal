package com.goforer.phogal.data.model.remote.response

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meta(
    var is_end: Boolean,
    var pageable_count: Int,
    var total_count: Int
) : BaseModel(), Parcelable