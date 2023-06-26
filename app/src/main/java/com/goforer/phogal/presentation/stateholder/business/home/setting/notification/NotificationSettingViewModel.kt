package com.goforer.phogal.presentation.stateholder.business.home.setting.notification

import androidx.lifecycle.viewModelScope
import com.goforer.phogal.data.datasource.local.LocalDataSource
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.model.remote.response.gallery.common.User
import com.goforer.phogal.presentation.stateholder.business.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationSettingViewModel
@Inject
constructor(
    private val localDataSource: LocalDataSource
) : BaseViewModel<User>() {

    companion object {
        const val FOLLOWING_NOTIFICATION_SETTING = 0
        const val LATEST_NOTIFICATION_SETTING = 1
        const val COMMUNITY_NOTIFICATION_SETTING = 2
    }

    override fun trigger(replyCount: Int, params: Params) {
        when(params.args[0]) {
            FOLLOWING_NOTIFICATION_SETTING -> {
                viewModelScope.launch {
                    localDataSource.enabledFollowingNotification = params.args[1] as Boolean
                }
            }
            LATEST_NOTIFICATION_SETTING -> {
                viewModelScope.let {
                    localDataSource.enabledLatestNotification = params.args[1] as Boolean
                }
            }
            COMMUNITY_NOTIFICATION_SETTING -> {
                viewModelScope.let {
                    localDataSource.enableCommunityNotification = params.args[1] as Boolean
                }
            }
        }
    }

    fun getNotificationSetting(value: Int): Boolean {
        return when(value) {
            FOLLOWING_NOTIFICATION_SETTING -> localDataSource.enabledLatestNotification
            LATEST_NOTIFICATION_SETTING -> localDataSource.enabledLatestNotification
            COMMUNITY_NOTIFICATION_SETTING -> localDataSource.enableCommunityNotification
            else -> localDataSource.enabledLatestNotification
        }
    }
}