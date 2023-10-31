package com.goforer.phogal.data.model.remote.response.setting

import android.os.Parcelable
import com.goforer.phogal.data.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileUiState(
    val id: Int = 0,
    val name: String = "",
    val sex: String = "",
    var favor: Boolean = false,
    var followed : Boolean = false,
    val email: String = "",
    val profileImage: String = "",
    val personality: String = "",
    val cellphone: String = "",
    val address: String = "",
    val birthday: String = "",
    val reputation: String = "",
    var deleted: Boolean = false
) : BaseModel(), Parcelable
