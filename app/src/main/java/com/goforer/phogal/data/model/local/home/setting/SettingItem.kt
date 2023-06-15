package com.goforer.phogal.data.model.local.home.setting

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class SettingItem(
    val text: String,
    val drawable: Int
) : BaseModel(), Parcelable
