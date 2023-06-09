package com.goforer.phogal.presentation.ui.navigation.destination

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

interface PhogalDestination {
    val icon: ImageVector
    val route: String
    val screen: @Composable (NavHostController, NavBackStackEntry, String) -> Unit

    companion object {
        // Photo Bottom Navigation
        internal const val photosHomeRoute = "photoHome"
        internal const val searchPhotosStartRoute = "photoHome/searchPhotos"
        internal const val pictureRoute = "photoHome/picture"
        internal const val userPhotosRoute = "photoHome/userPhotos"
        internal const val webViewRoute = "photoHome/webView"

        // Community Bottom Navigation
        internal const val popularPhotosHomeRoute = "popularPhotosHome"
        internal const val popularPhotosStartRoute = "popularPhotosHome/popularPhotos"

        // Notification Bottom Navigation
        internal const val notificationHomeRoute = "notificationHome"
        internal const val notificationsStartRoute = "notificationHome/notifications"
        internal const val notificationRoute = "notificationHome/notification"

        // Setting Bottom Navigation
        internal const val settingHomeRoute = "settingHome"
        internal const val settingStartRoute = "settingHome/setting"
        internal const val settingBookmarkedPhotosRoute = "settingHome/bookmarkedPhotos"
        internal const val settingFollowingUsersRoute = "settingHome/followingUsers"
        internal const val settingNotificationRoute = "settingHome/notificationSetting"
    }
}