package com.goforer.phogal.presentation.stateholder.business.home.common.gallery.follow

import androidx.lifecycle.viewModelScope
import com.goforer.base.storage.LocalStorage
import com.goforer.phogal.data.model.remote.response.gallery.common.User
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.presentation.stateholder.business.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowViewModel
@Inject
constructor() : BaseViewModel<User>() {
    @Inject
    lateinit var localStorage: LocalStorage

    private val _followingUsersUiState = MutableStateFlow(mutableListOf<User>())
    val followingUsersState: StateFlow<MutableList<User>> = _followingUsersUiState

    fun setUserFollow(user: User) {
        localStorage.setFollowingUser(user)
    }

    override fun trigger(replyCount: Int, params: Params) {
        viewModelScope.launch {
            flowOf(
                localStorage.getFollowingUsers()
            ).stateIn(viewModelScope)
             .collectLatest { users ->
                 _followingUsersUiState.value = users ?: mutableListOf()
             }
        }
    }

    fun isUserFollowed(user: User) = localStorage.isUserFollowed(user)
}