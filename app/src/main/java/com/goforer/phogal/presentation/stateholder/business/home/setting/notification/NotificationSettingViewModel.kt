package com.goforer.phogal.presentation.stateholder.business.home.setting.notification

import androidx.lifecycle.viewModelScope
import com.goforer.phogal.data.datasource.local.LocalDataSource
import com.goforer.phogal.data.model.remote.response.gallery.common.User
import com.goforer.phogal.presentation.stateholder.business.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationSettingViewModel
@Inject
constructor(
    private val localDataSource: LocalDataSource
) : BaseViewModel<User>() {
    fun setFollowingNotification(toggled: Boolean) {
        viewModelScope.let {
            localDataSource.enabledFollowingNotification = toggled
        }
    }

    fun getFollowingNotification(): Boolean {
        return localDataSource.enabledFollowingNotification
    }

    fun setLatestNotification(toggled: Boolean) {
        viewModelScope.let {
            localDataSource.enabledLatestNotification = toggled
        }
    }

    fun getLatestNotification(): Boolean {
        return localDataSource.enabledLatestNotification
    }

    fun setCommunityNotification(toggled: Boolean) {
        viewModelScope.let {
            localDataSource.enableCommunityNotification = toggled
        }
    }

    fun getCommunityNotification(): Boolean {
        return localDataSource.enableCommunityNotification
    }
}