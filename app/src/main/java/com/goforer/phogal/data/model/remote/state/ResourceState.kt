package com.goforer.phogal.data.model.remote.state

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import com.goforer.phogal.data.datasource.network.response.Status
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ResourceState<T>(
    val resourceStateFlow: @RawValue T? = null,
    var status: Status = Status.SUCCESS
) : BaseModel(), Parcelable