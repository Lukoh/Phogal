package com.goforer.phogal.presentation.stateholder.business.home.common.follow

import androidx.lifecycle.viewModelScope
import com.goforer.phogal.data.datasource.local.LocalDataSource
import com.goforer.phogal.data.model.remote.response.gallery.common.User
import com.goforer.phogal.data.datasource.network.api.Params
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
constructor(
    private val localDataSource: LocalDataSource
) : BaseViewModel<User>() {
    private val _followingUsersUiState = MutableStateFlow(mutableListOf<User>())
    val followingUsersState: StateFlow<MutableList<User>> = _followingUsersUiState

    override fun trigger(replyCount: Int, params: Params) {
        viewModelScope.launch {
            flowOf(
                localDataSource.getFollowingUsers()
            ).stateIn(viewModelScope)
             .collectLatest { users ->
                 _followingUsersUiState.value = users ?: mutableListOf()
             }
        }
    }

    fun setUserFollow(user: User) {
        localDataSource.setFollowingUser(user)
    }

    fun isUserFollowed(user: User) = localDataSource.isUserFollowed(user)
}