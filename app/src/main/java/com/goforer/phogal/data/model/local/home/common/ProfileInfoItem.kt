package com.goforer.phogal.data.model.local.home.common

import android.os.Parcelable
import androidx.compose.ui.graphics.painter.Painter
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ProfileInfoItem(
    val text: String,
    val painter: @RawValue Painter,
    val position: Int
) : BaseModel(), Parcelable