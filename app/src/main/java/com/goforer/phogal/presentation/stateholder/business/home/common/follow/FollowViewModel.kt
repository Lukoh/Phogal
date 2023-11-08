package com.goforer.phogal.presentation.stateholder.business.home.common.follow

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.goforer.phogal.data.datasource.local.LocalDataSource
import com.goforer.phogal.data.model.remote.response.gallery.common.UserUiState
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.datasource.network.response.Status
import com.goforer.phogal.presentation.stateholder.business.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
    private val localDataSource: LocalDataSource
) : BaseViewModel<UserUiState>() {
    private val _uiState = MutableStateFlow(mutableListOf<UserUiState>())
    val uiState: StateFlow<MutableList<UserUiState>> = _uiState

    override fun trigger(replyCount: Int, params: Params) {
        viewModelScope.launch {
            flowOf(
                localDataSource.getFollowingUsers()
            ).onStart {
                Status.LOADING
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = mutableListOf()
            ).collectLatest { users ->
                _uiState.value = users as MutableList<UserUiState>
            }
        }
    }

    fun setUserFollow(user: UserUiState) {
        localDataSource.setFollowingUser(user)
    }

    fun isUserFollowed(user: UserUiState) = localDataSource.isUserFollowed(user)
}