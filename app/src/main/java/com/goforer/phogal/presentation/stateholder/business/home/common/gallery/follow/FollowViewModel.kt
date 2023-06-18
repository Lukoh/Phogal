package com.goforer.phogal.presentation.stateholder.business.home.common.gallery.follow

import com.goforer.base.storage.LocalStorage
import com.goforer.phogal.data.model.remote.response.gallery.common.User
import com.goforer.phogal.presentation.stateholder.business.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowViewModel
@Inject
constructor() : BaseViewModel<User>() {
    @Inject
    lateinit var localStorage: LocalStorage

    fun setUserFollow(user: User) {
        localStorage.setFollowingUser(user)
    }

    fun getFollowingUsers(): MutableList<User>? = localStorage.getFollowingUsers()

    fun isUserFollowed(user: User) = localStorage.isUserFollowed(user)
}