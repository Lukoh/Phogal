package com.goforer.base.extension

import com.goforer.phogal.data.model.remote.response.gallery.common.UserUiState
import com.google.gson.Gson

fun String.toUserUiState(): UserUiState = Gson().fromJson(this, UserUiState::class.java)