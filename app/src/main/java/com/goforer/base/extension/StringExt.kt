package com.goforer.base.extension

import com.goforer.phogal.data.model.remote.response.gallery.common.User
import com.google.gson.Gson

fun String.toUser(): User = Gson().fromJson(this, User::class.java)